package rina.efcp.impl;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import rina.aux.QueueSubscriptor;
import rina.efcp.api.DTPPDU;
import rina.efcp.api.DataTransferAE;
import rina.efcp.api.FlowControlOnlyDTCPPDU;
import rina.efcp.api.PDU;
import rina.ipcmanager.api.IPCManager;

/**
 * Reads the incoming flow queues, gets SDUs from them, 
 * applies DTP and posts to the right socket.
 * @author eduardgrasa
 *
 */
public class IncomingEFCPQueuesReader implements Runnable, QueueSubscriptor{
	
	private static final Log log = LogFactory.getLog(IncomingEFCPQueuesReader.class);
	
	/**
	 * The IPC Manager
	 */
	private IPCManager ipcManager = null;
	
	/**
	 * The queues from incoming flows
	 */
	private BlockingQueue<Integer> queuesReadyToBeRead = null;
	
	/**
	 * The mappings of portId to connection
	 */
	private Map<Long, DTAEIState> connectionStatesByConnectionId = null;
	
	/**
	 * the Data Transfer AE
	 */
	private DataTransferAE dataTransferAE = null;
	
	private boolean end = false;
	
	public IncomingEFCPQueuesReader(IPCManager ipcManager, Map<Long, DTAEIState> connectionStatesByConnectionId, 
			DataTransferAE dataTransferAE){
		this.ipcManager = ipcManager;
		this.dataTransferAE = dataTransferAE;
		this.queuesReadyToBeRead = new LinkedBlockingQueue<Integer>();
		this.connectionStatesByConnectionId = connectionStatesByConnectionId;
	}
		
	public void stop(){
		this.end = true;
		try{
			this.queuesReadyToBeRead.put(new Integer(-1));
		}catch(Exception ex){
			log.error(ex);
		}
	}

	public void queueReadyToBeRead(int queueId) {
		try {
			this.queuesReadyToBeRead.put(new Integer(queueId));
		} catch (InterruptedException e) {
			log.error(e);
		}
	}
	
	/**
	 * Read the data from the queues and process it
	 */
	public void run() {
		long connectionEndpointId = -1;
		PDU pdu = null;
		
		while(!end){
			try{
				connectionEndpointId = this.queuesReadyToBeRead.take().longValue();
				if (connectionEndpointId < 0){
					break;
				}
				pdu = this.dataTransferAE.getIncomingConnectionQueue(connectionEndpointId).take();
				this.processEFCPPDU(pdu, connectionStatesByConnectionId.get(new Long(connectionEndpointId)));
			}catch(Exception ex){
				log.error("Problems reading the identity of the next queue to read. ", ex);
			}
		}
	}
	
	/**
	 * Update DTAEI state and send SDU to N-portId
	 * @param sdu
	 * @param flowState
	 */
	private void processEFCPPDU(PDU pdu, DTAEIState state){
		if (state == null){
			log.error("Received a PDU with an unrecognized Connection ID: "+pdu.getConnectionId());
			return;
		}
		
		if (pdu instanceof DTPPDU){
			this.processDTPPDU((DTPPDU)pdu, state);
		}else if (pdu instanceof FlowControlOnlyDTCPPDU){
			this.processFlowControlOnlyDTCPPDU((FlowControlOnlyDTCPPDU)pdu, state);
		}else{
			log.error("Received a PDU with of an unrecognized type: "+pdu.toString());
		}
	}
	
	/**
	 * Update DTAEI state and send SDU to N-portId
	 * @param DTP PDU
	 * @param flowState
	 */
	private void processDTPPDU(DTPPDU pdu, DTAEIState state){
		byte[] sdu = pdu.getUserData();
		if (sdu.length == 0){
			//TODO do something special?
		}else{
			//Deliver the PDU to the portId
			try{
				this.ipcManager.getIncomingFlowQueue((int)state.getPortId()).writeDataToQueue(sdu);
			}catch(Exception ex){
				log.error("Problems delivering an SDU to N-portId "+state.getPortId()+". Dropping the SDU.",ex);
			}
		}
		
		//Update DTAEI state
		synchronized(state){
			state.incrementLastSequenceDelivered();
		}
	}
	
	/**
	 * When the PDU is received then: If Creditbased Then SndrCredit := PDU(RtWindEdge) SndLeftWindEdge := UpdateCredit(Ack, RtWindEdge) Fi 
	 * If Ratebased present Then SndrRate := PDU(Rate) TimePeriod ;= TimeUnit Fi 
	 * If WaitQ not Empty Then Send as many PDUs as possible, given the current allocation Fi
	 * @param pdu the Flow Control Only DTCP PDU
	 * @param state
	 */
	private void processFlowControlOnlyDTCPPDU(FlowControlOnlyDTCPPDU pdu, DTAEIState state){
		
	}
}
