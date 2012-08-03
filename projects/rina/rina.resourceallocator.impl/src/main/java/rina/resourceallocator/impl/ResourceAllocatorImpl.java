package rina.resourceallocator.impl;

import rina.ipcprocess.api.IPCProcess;
import rina.resourceallocator.api.BaseResourceAllocator;
import rina.resourceallocator.api.NMinus1FlowManager;
import rina.resourceallocator.impl.flowmanager.NMinus1FlowManagerImpl;

/**
 * The Resource Allocator (RA) is the core of management in the IPC Process. 
 * The degree of decentralization depends on the policies and how it is used. The RA has a set of meters 
 * and dials that it can manipulate. The meters fall in 3 categories:
 * 		� Traffic characteristics from the user of the DIF
 * 		� Traffic characteristics of incoming and outgoing flows
 * 		� Information from other members of the DIF
 * The Dials:
 * 		� Creation/Deletion of QoS Classes
 * 		� Data Transfer QoS Sets
 *		� Modifying Data Transfer Policy Parameters
 * 		� Creation/Deletion of RMT Queues
 * 		� Modify RMT Queue Servicing
 * 		� Creation/Deletion of (N-1)-flows
 * 		� Assignment of RMT Queues to (N-1)-flows
 * 		� Forwarding Table Generator Output
 * 
 * @author eduardgrasa
 *
 */
public class ResourceAllocatorImpl extends BaseResourceAllocator{
	
	private NMinus1FlowManagerImpl nMinus1FlowManager = null;

	public ResourceAllocatorImpl(){
		nMinus1FlowManager = new NMinus1FlowManagerImpl();
	}
	
	public NMinus1FlowManager getNMinus1FlowManager() {
		return this.nMinus1FlowManager;
	}
	
	public void setIPCProcess(IPCProcess ipcProcess){
		super.setIPCProcess(ipcProcess);
		this.nMinus1FlowManager.setIPCProcess(ipcProcess);
	}

}
