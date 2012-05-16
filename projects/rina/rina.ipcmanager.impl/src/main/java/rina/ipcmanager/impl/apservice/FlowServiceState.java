package rina.ipcmanager.impl.apservice;

import java.net.Socket;

import rina.cdap.api.message.CDAPMessage;
import rina.ipcservice.api.FlowService;
import rina.ipcservice.api.IPCService;

/**
 * Contains the information about the state of a flow service request
 * @author eduardgrasa
 *
 */
public class FlowServiceState {
	
	public enum Status {NULL, ALLOCATION_REQUESTED, ALLOCATED, DEALLOCATION_REQUESTED};
	
	/**
	 * The parameters of the requested flow service (source app, destination app, 
	 * QoS parameters, portId)
	 */
	private FlowService flowService = null;
	
	/**
	 * The socket to communicate with the application that is using the flow service
	 */
	private Socket socket = null;
	
	private Status status = Status.NULL;
	
	/**
	 * The IPC Process that is handling our request
	 */
	private IPCService ipcService = null;
	
	/**
	 * The latest relevant CDAP message received from the application
	 */
	private CDAPMessage cdapMessage = null;
	
	/**
	 * The socket reader that reads the messages coming from the application library
	 */
	private TCPSocketReader tcpSocketReader = null;

	public synchronized FlowService getFlowService() {
		return flowService;
	}

	public synchronized void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}

	public synchronized Socket getSocket() {
		return socket;
	}

	public synchronized void setSocket(Socket socket) {
		this.socket = socket;
	}

	public synchronized Status getStatus() {
		return status;
	}

	public synchronized void setStatus(Status status) {
		this.status = status;
	}

	public synchronized void setIpcService(IPCService ipcService) {
		this.ipcService = ipcService;
	}

	public synchronized IPCService getIpcService() {
		return ipcService;
	}

	public synchronized void setCdapMessage(CDAPMessage cdapMessage) {
		this.cdapMessage = cdapMessage;
	}

	public synchronized CDAPMessage getCdapMessage() {
		return cdapMessage;
	}

	public synchronized void setTcpSocketReader(TCPSocketReader tcpSocketReader) {
		this.tcpSocketReader = tcpSocketReader;
	}

	public synchronized TCPSocketReader getTcpSocketReader() {
		return tcpSocketReader;
	}
	
	@Override
	public boolean equals(Object object){
		if (object == null){
			return false;
		}
		
		if (!(object instanceof FlowServiceState)){
			return false;
		}
		
		FlowServiceState candidate = (FlowServiceState) object;
		
		if (candidate.getFlowService() == null){
			return false;
		}
		
		return (candidate.getFlowService().getPortId() == this.getFlowService().getPortId());
	}
	
}