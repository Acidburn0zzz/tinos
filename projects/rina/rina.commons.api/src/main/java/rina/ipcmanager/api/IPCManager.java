package rina.ipcmanager.api;

import rina.flowallocator.api.FlowAllocatorInstance;
import rina.flowallocator.api.message.Flow;

/**
 * The IPC Manager is the component of a DAF that manages the local IPC resources. In its current implementation it 
 * manages IPC Processes (creates/destroys them), and serves as a broker between applications and IPC Processes. Applications 
 * can use the RINA library to establish a connection to the IPC Manager and interact with the RINA stack.
 * @author eduardgrasa
 *
 */
public interface IPCManager {
	
	/**
	 * Invoked by the Flow Allocator Instance when it receives a new create flow request. The IPCManager has to call the destination application 
	 * to see wether it accepts or not the flow allocation request.
	 * @param flow
	 * @param ipcProcess
	 */
	public void createFlowRequestMessageReceived(Flow flow, FlowAllocatorInstance flowAllocatorInstance);
	
	/**
	 * Setter for the InterDIFDirectory
	 * @param idd
	 */
	public void setInterDIFDirectory(InterDIFDirectory idd);

}
