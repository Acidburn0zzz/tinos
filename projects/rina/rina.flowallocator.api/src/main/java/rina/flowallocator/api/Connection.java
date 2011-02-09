package rina.flowallocator.api;

import java.util.List;
import java.util.Map;
import java.util.Timer;

import rina.ipcservice.api.ApplicationProcessNamingInfo;
import rina.ipcservice.api.QoSParameters;
import rina.utils.types.Unsigned;

/**
 * Captures the state of a single connection
 * @author eduardgrasa
 */
public class Connection {
	/**
	 * The application that requested the flow
	 */
	private ApplicationProcessNamingInfo sourceNamingInfo = null;
	
	/**
	 * The address of the IPC process that is the source of this connection
	 */
	private byte[] sourceAddress = null;
	
	/**
	 * The port-id returned to the Application process that requested the flow. This port-id is used for 
	 * the life of the connection.
	 */
	private Unsigned sourcePortId = null;
	
	/**
	 * The destination application of the flow
	 */
	private ApplicationProcessNamingInfo destinationNamingInfo = null;
	
	/**
	 * The address of the IPC process that is the destination of this connection
	 */
	private byte[] destinationAddress = null;
	
	/**
	 * The port-id returned to the destination Application process. This port-id is used for 
	 * the life of the connection.
	 */
	private Unsigned destinationPortId = null;
	
	/**
	 * Identification of the QoS cube associated with this connection
	 */
	private QoSCube qosCube = null;
	
	/**
	 * The list of parameters from the Allocate_Request.submit call that generated this connection
	 */
	private QoSParameters qosParameters = null;

	/**
	 * The list of policies that are used to control this connection. NOTE: Does this provide 
	 * anything beyond the list used within the QoS-cube? Can we override or specialize those, 
	 * somehow?
	 */
	private List<String> policies = null;
	
	/**
	 * The merged list of parameters from QoS.policy-Default-Parameters and QoS-Params.
	 */
	private Map<String, String> policyParameters = null;
	
	/**
	 * This is a list of the connectionIds that may be active fo this connection, generally no 
	 * more than two.
	 */
	private List<ConnectionId> connectionIds = null;
	
	/**
	 * This is the connectionId that is currently "active" for sending data, although data may 
	 * still arrive on earlier flows.
	 */
	private ConnectionId currentConnectionId = null;
	
	/**
	 * More than one connection can be active within a flow and a SDU can be fragmented across two 
	 * connections. Therefore, the reassembly queue has to be associated with the flow, not with 
	 * individual connections.
	 * NOTE: This is just a placeholder for the reassembly queue, obviously it won't be a string.
	 */
	private List<Object> reassemblyQueue = null;
	
	/**
	 * This is essentially, QoS.MaxGap. The "gap" is in terms of SDU, not PDU. Since SDUs can cross 
	 * flows, SDUs also need to be sequenced, by the delimiting module.
	 */
	private int maxGapAllowed = 0;
	
	/**
	 * sduGapTimer
	 */
	private Timer sduGapTimer = null;
	
	/**
	 * The state of the communication with the remote FAI
	 */
	private String state = null;
	
	/**
	 * TBD, may not be a string.
	 */
	private String accessControl = null;
	
	/**
	 * The retries to create a flow
	 */
	private int craeteFlowRetries = 0;

	public ApplicationProcessNamingInfo getSourceNamingInfo() {
		return sourceNamingInfo;
	}

	public void setSourceNamingInfo(ApplicationProcessNamingInfo sourceNamingInfo) {
		this.sourceNamingInfo = sourceNamingInfo;
	}

	public byte[] getSourceAddress() {
		return sourceAddress;
	}

	public void setSourceAddress(byte[] sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	public Unsigned getSourcePortId() {
		return sourcePortId;
	}

	public void setSourcePortId(Unsigned sourcePortId) {
		this.sourcePortId = sourcePortId;
	}

	public ApplicationProcessNamingInfo getDestinationNamingInfo() {
		return destinationNamingInfo;
	}

	public void setDestinationNamingInfo(ApplicationProcessNamingInfo destinationNamingInfo) {
		this.destinationNamingInfo = destinationNamingInfo;
	}

	public byte[] getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(byte[] destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public Unsigned getDestinationPortId() {
		return destinationPortId;
	}

	public void setDestinationPortId(Unsigned destinationPortId) {
		this.destinationPortId = destinationPortId;
	}

	public QoSCube getQosCube() {
		return qosCube;
	}

	public void setQosCube(QoSCube qosCube) {
		this.qosCube = qosCube;
	}

	public List<String> getPolicies() {
		return policies;
	}

	public void setPolicies(List<String> policies) {
		this.policies = policies;
	}

	public Map<String, String> getPolicyParameters() {
		return policyParameters;
	}

	public void setPolicyParameters(Map<String, String> policyParameters) {
		this.policyParameters = policyParameters;
	}

	public List<ConnectionId> getConnectionIds() {
		return connectionIds;
	}

	public void setConnectionIds(List<ConnectionId> connectionIds) {
		this.connectionIds = connectionIds;
	}

	public ConnectionId getCurrentConnectionId() {
		return currentConnectionId;
	}

	public void setCurrentConnectionId(ConnectionId currentConnectionId) {
		this.currentConnectionId = currentConnectionId;
	}

	public List<Object> getReassemblyQueue() {
		return reassemblyQueue;
	}

	public void setReassemblyQueue(List<Object> reassemblyQueue) {
		this.reassemblyQueue = reassemblyQueue;
	}

	public int getMaxGapAllowed() {
		return maxGapAllowed;
	}

	public void setMaxGapAllowed(int maxGapAllowed) {
		this.maxGapAllowed = maxGapAllowed;
	}

	public Timer getSduGapTimer() {
		return sduGapTimer;
	}

	public void setSduGapTimer(Timer sduGapTimer) {
		this.sduGapTimer = sduGapTimer;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAccessControl() {
		return accessControl;
	}

	public void setAccessControl(String accessControl) {
		this.accessControl = accessControl;
	}

	public int getCraeteFlowRetries() {
		return craeteFlowRetries;
	}

	public void setCraeteFlowRetries(int craeteFlowRetries) {
		this.craeteFlowRetries = craeteFlowRetries;
	}
	
	public QoSParameters getQosParameters() {
		return qosParameters;
	}

	public void setQosParameters(QoSParameters qosParameters) {
		this.qosParameters = qosParameters;
	}
	
	@Override
	public boolean equals(Object candidate){
		if (candidate == null){
			return false;
		}
		
		if (!(candidate instanceof Connection)){
			return false;
		}
		
		Connection connection = (Connection) candidate;
		
		if (!(connection.getSourceAddress().equals(this.getSourceAddress()))){
			return false;
		}
		
		if (!(connection.getDestinationAddress().equals(this.getDestinationAddress()))){
			return false;
		}
		
		return connection.getCurrentConnectionId().equals(connection.getCurrentConnectionId());
	}
}
