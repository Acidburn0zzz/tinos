package rina.cdap.api;

import java.io.Serializable;


/**
 * CDAP message. Depending on the opcode, the following messages are possible:
 * M_CONNECT -> Common Connect Request. Initiate a connection from a source application to a destination application
 * M_CONNECT_R -> Common Connect Response. Response to M_CONNECT, carries connection information or an error indication
 * M_RELEASE -> Common Release Request. Orderly close of a connection
 * M_RELEASE_R -> Common Release Response. Response to M_RELEASE, carries final resolution of close operation
 * M_CREATE -> Create Request. Create an application object
 * M_CREATE_R -> Create Response. Response to M_CREATE, carries result of create request, including identification of the created object
 * M_DELETE -> Delete Request. Delete a specified application object
 * M_DELETE_R -> Delete Response. Response to M_DELETE, carries result of a deletion attempt
 * M_READ -> Read Request. Read the value of a specified application object
 * M_READ_R -> Read Response. Response to M_READ, carries part of all of object value, or error indication
 * M_CANCELREAD -> Cancel Read Request. Cancel a prior read issued using the M_READ for which a value has not been completely returned
 * M_CANCELREAD_R -> Cancel Read Response. Response to M_CANCELREAD, indicates outcome of cancellation
 * M_WRITE -> Write Request. Write a specified value to a specified application object
 * M_WRITE_R -> Write Response. Response to M_WRITE, carries result of write operation
 * M_START -> Start Request. Start the operation of a specified application object, used when the object has operational and non operational states
 * M_START_R -> Start Response. Response to M_START, indicates the result of the operation
 * M_STOP -> Stop Request. Stop the operation of a specified application object, used when the object has operational an non operational states
 * M_STOP_R -> Stop Response. Response to M_STOP, indicates the result of the operation.
 */
public class CDAPMessage implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public enum Opcode {M_CONNECT, M_CONNECT_R, M_RELEASE, M_RELEASE_R, M_CREATE, M_CREATE_R, 
		M_DELETE, M_DELETE_R, M_READ, M_READ_R, M_CANCELREAD, M_CANCELREAD_R, M_WRITE, 
		M_WRITE_R, M_START, M_START_R, M_STOP, M_STOP_R};
		
	public enum Flags {F_INCOMPLETE, FLAG2};
	
	/** 
	 * AbstractSyntaxID (int32), mandatory. The specific version of the 
	 * CDAP protocol message declarations that the message conforms to 
	 */
	private int absSyntax = -1;
	
	/**
	 * AuthenticationMechanismName (string), optional, not validated by CDAP. 
	 * Identification of the method to be used by the destination application to
	 * authenticate the source application
	 */
	private String authMech = null;
	
	/**
	 * AuthenticationValue (bytes), optional, not validated by CDAP.
	 * Authentication information accompanying authMech, format and value 
	 * appropiate to the selected authMech
	 */
	private byte[] authValue = null;
	
	/**
	 * DestinationApplication-Entity-Instance-Id (string), optional, not validated by CDAP.
	 * Specific instance of the Application Entity that the source application
	 * wishes to connect to in the destination application.
	 */
	private String destAEInst = null;
	
	/**
	 * DestinationApplication-Entity-Name (string), mandatory (optional for the response).
	 * Name of the Application Entity that the source application wishes
	 * to connect to in the destination application.
	 */
	private String destAEName = null;
	
	/**
	 * DestinationApplication-Process-Instance-Id (string), optional, not validated by CDAP.
	 * Name of the Application Process Instance that the source wishes to
	 * connect to a the destination.
	 */
	private String destApInst = null;
	
	/**
	 * DestinationApplication-Process-Name (string), mandatory (optional for the response).
	 * Name of the application process that the source application wishes to connect to 
	 * in the destination application
	 */
	private String destApName = null;
	
	/**
	 * Filter (bytes). Filter predicate function to be used to determine whether an operation
	 * is to be applied to the designated object (s).
	 */
	private byte[] filter = null;
		
	/**
	 * flags (enm, int32), conditional, may be required by CDAP.
	 * Set of Boolean values that modify the meaning of a 
	 * message in a uniform way when true.
	 */
	private Flags[] flags = null;
	
	/**
	 * InvokeID, (int32). Unique identifier provided to identify a request, used to
	 * identify subsequent associated messages.
	 */
	private int invokeID = -1;
	
	/**
	 * ObjectClass (string). Identifies the object class definition of the 
	 * addressed object.
	 */
	private String objClass = null;
	
	/**
	 * ObjectInstance (int64). Object instance uniquely identifies a single object
	 * with a specific ObjectClass and ObjectName in an application's RIB. Either 
	 * the ObjectClass and ObjectName or this value may be used, if the ObjectInstance
	 * value is known. If a class and name are supplied in an operation, 
	 * an ObjectInstance value may be returned, and that may be used in future operations
	 * in lieu of objClass and objName for the duration of this connection.
	 */
	private long objInst = -1;
	
	/**
	 * ObjectName (string). Identifies a named object that the operation is 
	 * to be applied to. Object names identify a unique object of the designated 
	 * ObjectClass within an application.
	 */
	private String objName = null;
	
	/**
	 * ObjectValue (bytes). The value of the object.
	 */
	private byte[] objValue = null;
		
	/**
	 * Opcode (enum, int32), mandatory.
	 * Message type of this message.
	 */
	private Opcode opCode = null;
	
	/**
	 * Result (int32). Mandatory in the responses, forbidden in the requests
	 * The result of an operation, indicating its success (which has the value zero, 
	 * the default for this field), partial success in the case of 
	 * synchronized operations, or reason for failure
	 */
	private int result = -1;
	
	/**
	 * Result-Reason (string), optional in the responses, forbidden in the requests
	 * Additional explanation of the result
	 */
	private String resultReason = null;
	
	/**
	 * Scope (int32). Specifies the depth of the object tree at 
	 * the destination application to which an operation (subject to filtering) 
	 * is to apply (if missing or present and having the value 0, the default, 
	 * only the targeted application's objects are addressed)
	 */
	private int scope = -1;
	
	/**
	 * SourceApplication-Entity-Instance-Id (string).
	 * AE instance within the application originating the message
	 */
	private String srcAEInst = null;
	
	/**
	 * SourceApplication-Entity-Name (string).
	 * Name of the AE within the application originating the message
	 */
	private String srcAEName = null;
	
	/**
	 * SourceApplication-Process-Instance-Id (string), optional, not validated by CDAP.
	 * Application instance originating the message
	 */
	private String srcApInst = null;
	
	/**
	 * SourceApplicatio-Process-Name (string), mandatory (optional in the response).
	 * Name of the application originating the message
	 */
	private String srcApName = null;
	
	/**
	 * Version (int32). Mandatory in connect request and response, optional otherwise.
	 * Version of RIB and object set to use in the conversation. Note that the 
	 * abstract syntax refers to the CDAP message syntax, while version refers to the 
	 * version of the AE RIB objects, their values, vocabulary of object id's, and 
	 * related behaviors that are subject to change over time. See text for details
	 * of use.
	 */
	private int version = -1;
	
	private CDAPMessage(){
	}
	
	public static CDAPMessage getOpenConnectionRequestMessage(int absSyntax, String authMech, 
			byte[] authValue, String destAEInst, String destAEName, String destApInst,
			String destApName, String srcAEInst, String srcAEName, String srcApInst,
			String srcApName, int version) throws CDAPException{
		CDAPMessage cdapMessage = new CDAPMessage();
		cdapMessage.setAbsSyntax(absSyntax);
		cdapMessage.setAuthMech(authMech);
		cdapMessage.setAuthValue(authValue);
		cdapMessage.setDestAEInst(destAEInst);
		cdapMessage.setDestAEName(destAEName);
		cdapMessage.setDestApInst(destApInst);
		cdapMessage.setDestApInst(destApInst);
		cdapMessage.setDestApName(destApName);
		cdapMessage.setOpCode(Opcode.M_CONNECT);
		cdapMessage.setSrcAEInst(srcAEInst);
		cdapMessage.setSrcAEName(srcAEName);
		cdapMessage.setSrcApInst(srcApInst);
		cdapMessage.setSrcApName(srcApName);
		cdapMessage.setVersion(version);
		CDAPMessageValidator.validate(cdapMessage);
		return cdapMessage;
	}
	
	public static CDAPMessage getOpenConnectionResponseMessage(int absSyntax, String authMech, 
			byte[] authValue, String destAEInst, String destAEName, String destApInst,
			String destApName, int result, String resultReason, String srcAEInst, String srcAEName, 
			String srcApInst, String srcApName, int version) throws CDAPException{
		CDAPMessage cdapMessage = new CDAPMessage();
		cdapMessage.setAbsSyntax(absSyntax);
		cdapMessage.setAuthMech(authMech);
		cdapMessage.setAuthValue(authValue);
		cdapMessage.setDestAEInst(destAEInst);
		cdapMessage.setDestAEName(destAEName);
		cdapMessage.setDestApInst(destApInst);
		cdapMessage.setDestApInst(destApInst);
		cdapMessage.setDestApName(destApName);
		cdapMessage.setOpCode(Opcode.M_CONNECT_R);
		cdapMessage.setResult(result);
		cdapMessage.setResultReason(resultReason);
		cdapMessage.setSrcAEInst(srcAEInst);
		cdapMessage.setSrcAEName(srcAEName);
		cdapMessage.setSrcApInst(srcApInst);
		cdapMessage.setSrcApName(srcApName);
		cdapMessage.setVersion(version);
		CDAPMessageValidator.validate(cdapMessage);
		return cdapMessage;
	}
	
	public static CDAPMessage getReleaseConnectionRequestMessage(Flags[] flags, int invokeID, 
			String srcAEInst, String srcAEName) throws CDAPException{
		CDAPMessage cdapMessage = new CDAPMessage();
		cdapMessage.setFlags(flags);
		cdapMessage.setInvokeID(invokeID);
		cdapMessage.setOpCode(Opcode.M_RELEASE);
		cdapMessage.setSrcAEInst(srcAEInst);
		cdapMessage.setSrcAEName(srcAEName);
		CDAPMessageValidator.validate(cdapMessage);
		return cdapMessage;
	}
	
	public static CDAPMessage getReleaseConnectionResponseMessage(Flags[] flags, int invokeID,  
			int result, String resultReason, String srcAEInst, String srcAEName) throws CDAPException{
		CDAPMessage cdapMessage = new CDAPMessage();
		cdapMessage.setFlags(flags);
		cdapMessage.setInvokeID(invokeID);
		cdapMessage.setOpCode(Opcode.M_RELEASE_R);
		cdapMessage.setResult(result);
		cdapMessage.setResultReason(resultReason);
		cdapMessage.setSrcAEInst(srcAEInst);
		cdapMessage.setSrcAEName(srcAEName);
		CDAPMessageValidator.validate(cdapMessage);
		return cdapMessage;
	}
	
	public static CDAPMessage getCreateObjectRequestMessage(byte[] filter, Flags[] flags, 
			int invokeID, String objClass, long objInst, String objName, byte[] objValue, 
			int scope) throws CDAPException{
		CDAPMessage cdapMessage = new CDAPMessage();
		cdapMessage.setFilter(filter);
		cdapMessage.setFlags(flags);
		cdapMessage.setInvokeID(invokeID);
		cdapMessage.setObjClass(objClass);
		cdapMessage.setObjInst(objInst);
		cdapMessage.setObjName(objName);
		cdapMessage.setObjValue(objValue);
		cdapMessage.setOpCode(Opcode.M_CREATE);
		cdapMessage.setScope(scope);
		CDAPMessageValidator.validate(cdapMessage);
		return cdapMessage;
	}
	
	public static CDAPMessage getCreateObjectResponseMessage(Flags[] flags, int invokeID, 
			String objClass, long objInst, String objName, byte[] objValue, int result,
			String resultReason) throws CDAPException{
		CDAPMessage cdapMessage = new CDAPMessage();
		cdapMessage.setFlags(flags);
		cdapMessage.setInvokeID(invokeID);
		cdapMessage.setObjClass(objClass);
		cdapMessage.setObjInst(objInst);
		cdapMessage.setObjName(objName);
		cdapMessage.setObjValue(objValue);
		cdapMessage.setOpCode(Opcode.M_CREATE_R);
		cdapMessage.setResult(result);
		cdapMessage.setResultReason(resultReason);
		CDAPMessageValidator.validate(cdapMessage);
		return cdapMessage;
	}
	
	public static CDAPMessage getDeleteObjectRequestMessage(byte[] filter, Flags[] flags, int invokeID,
			String objClass, long objInst, String objName, int scope) throws CDAPException{
		CDAPMessage cdapMessage = new CDAPMessage();
		cdapMessage.setFilter(filter);
		cdapMessage.setFlags(flags);
		cdapMessage.setInvokeID(invokeID);
		cdapMessage.setObjClass(objClass);
		cdapMessage.setObjInst(objInst);
		cdapMessage.setObjName(objName);
		cdapMessage.setOpCode(Opcode.M_DELETE);
		cdapMessage.setScope(scope);
		CDAPMessageValidator.validate(cdapMessage);
		return cdapMessage;
	}
	
	public static CDAPMessage getDeleteObjectResponseMessage(Flags[] flags, int invokeID,  
			String objClass, long objInst, String objName, int result, String resultReason) throws CDAPException{
		CDAPMessage cdapMessage = new CDAPMessage();
		cdapMessage.setFlags(flags);
		cdapMessage.setInvokeID(invokeID);
		cdapMessage.setObjClass(objClass);
		cdapMessage.setObjInst(objInst);
		cdapMessage.setObjName(objName);
		cdapMessage.setOpCode(Opcode.M_DELETE_R);
		cdapMessage.setResult(result);
		cdapMessage.setResultReason(resultReason);
		CDAPMessageValidator.validate(cdapMessage);
		return cdapMessage;
	}
	
	public static CDAPMessage getStartObjectRequestMessage(byte[] filter, Flags[] flags, int invokeID,
			String objClass, byte[] objValue, long objInst, String objName, int scope) throws CDAPException{
		CDAPMessage cdapMessage = new CDAPMessage();
		cdapMessage.setFilter(filter);
		cdapMessage.setFlags(flags);
		cdapMessage.setInvokeID(invokeID);
		cdapMessage.setObjClass(objClass);
		cdapMessage.setObjInst(objInst);
		cdapMessage.setObjName(objName);
		cdapMessage.setObjValue(objValue);
		cdapMessage.setOpCode(Opcode.M_START);
		cdapMessage.setScope(scope);
		CDAPMessageValidator.validate(cdapMessage);
		return cdapMessage;
	}
	
	public static CDAPMessage getStartObjectResponseMessage(Flags[] flags, int invokeID, 
			int result, String resultReason) throws CDAPException{
		CDAPMessage cdapMessage = new CDAPMessage();
		cdapMessage.setFlags(flags);
		cdapMessage.setInvokeID(invokeID);
		cdapMessage.setOpCode(Opcode.M_START_R);
		cdapMessage.setResult(result);
		cdapMessage.setResultReason(resultReason);
		CDAPMessageValidator.validate(cdapMessage);
		return cdapMessage;
	}
	
	public static CDAPMessage getStopObjectRequestMessage(byte[] filter, Flags[] flags, int invokeID,
			String objClass, byte[] objValue, long objInst, String objName, int scope) throws CDAPException{
		CDAPMessage cdapMessage = new CDAPMessage();
		cdapMessage.setFilter(filter);
		cdapMessage.setFlags(flags);
		cdapMessage.setInvokeID(invokeID);
		cdapMessage.setObjClass(objClass);
		cdapMessage.setObjInst(objInst);
		cdapMessage.setObjName(objName);
		cdapMessage.setObjValue(objValue);
		cdapMessage.setOpCode(Opcode.M_STOP);
		cdapMessage.setScope(scope);
		CDAPMessageValidator.validate(cdapMessage);
		return cdapMessage;
	}
	
	public static CDAPMessage getStopObjectResponseMessage(Flags[] flags, int invokeID, 
			int result, String resultReason) throws CDAPException{
		CDAPMessage cdapMessage = new CDAPMessage();
		cdapMessage.setFlags(flags);
		cdapMessage.setInvokeID(invokeID);
		cdapMessage.setOpCode(Opcode.M_STOP_R);
		cdapMessage.setResult(result);
		cdapMessage.setResultReason(resultReason);
		CDAPMessageValidator.validate(cdapMessage);
		return cdapMessage;
	}
	
	public static CDAPMessage getReadObjectRequestMessage(byte[] filter, Flags[] flags, int invokeID,
			String objClass, long objInst, String objName, int scope) throws CDAPException{
		CDAPMessage cdapMessage = new CDAPMessage();
		cdapMessage.setFilter(filter);
		cdapMessage.setFlags(flags);
		cdapMessage.setInvokeID(invokeID);
		cdapMessage.setObjClass(objClass);
		cdapMessage.setObjInst(objInst);
		cdapMessage.setObjName(objName);
		cdapMessage.setOpCode(Opcode.M_READ);
		cdapMessage.setScope(scope);
		CDAPMessageValidator.validate(cdapMessage);
		return cdapMessage;
	}
	
	public static CDAPMessage getReadObjectResponseMessage(Flags[] flags, int invokeID, String objClass, 
			long objInst, String objName, byte[] objValue, int result, String resultReason) throws CDAPException{
		CDAPMessage cdapMessage = new CDAPMessage();
		cdapMessage.setFlags(flags);
		cdapMessage.setInvokeID(invokeID);
		cdapMessage.setObjClass(objClass);
		cdapMessage.setObjInst(objInst);
		cdapMessage.setObjName(objName);
		cdapMessage.setObjValue(objValue);
		cdapMessage.setOpCode(Opcode.M_READ_R);
		cdapMessage.setResult(result);
		cdapMessage.setResultReason(resultReason);
		CDAPMessageValidator.validate(cdapMessage);
		return cdapMessage;
	}
	
	public static CDAPMessage getWriteObjectRequestMessage(byte[] filter, Flags[] flags, int invokeID,
			String objClass, long objInst, byte[] objValue, String objName, int scope) throws CDAPException{
		CDAPMessage cdapMessage = new CDAPMessage();
		cdapMessage.setFilter(filter);
		cdapMessage.setFlags(flags);
		cdapMessage.setInvokeID(invokeID);
		cdapMessage.setObjClass(objClass);
		cdapMessage.setObjInst(objInst);
		cdapMessage.setObjName(objName);
		cdapMessage.setObjValue(objValue);
		cdapMessage.setOpCode(Opcode.M_WRITE);
		cdapMessage.setScope(scope);
		CDAPMessageValidator.validate(cdapMessage);
		return cdapMessage;
	}
	
	public static CDAPMessage getWriteObjectResponseMessage(Flags[] flags, int invokeID, 
			int result, String resultReason) throws CDAPException{
		CDAPMessage cdapMessage = new CDAPMessage();
		cdapMessage.setFlags(flags);
		cdapMessage.setInvokeID(invokeID);
		cdapMessage.setOpCode(Opcode.M_WRITE_R);
		cdapMessage.setResult(result);
		cdapMessage.setResultReason(resultReason);
		CDAPMessageValidator.validate(cdapMessage);
		return cdapMessage;
	}
	
	public static CDAPMessage getCancelReadRequestMessage(Flags[] flags, int invokeID) throws CDAPException{
		CDAPMessage cdapMessage = new CDAPMessage();
		cdapMessage.setFlags(flags);
		cdapMessage.setInvokeID(invokeID);
		cdapMessage.setOpCode(Opcode.M_CANCELREAD);
		CDAPMessageValidator.validate(cdapMessage);
		return cdapMessage;
	}
	
	public static CDAPMessage getCancelReadResponseMessage(Flags[] flags, int invokeID,
			int result, String resultReason) throws CDAPException{
		CDAPMessage cdapMessage = new CDAPMessage();
		cdapMessage.setFlags(flags);
		cdapMessage.setInvokeID(invokeID);
		cdapMessage.setOpCode(Opcode.M_CANCELREAD_R);
		cdapMessage.setResult(result);
		cdapMessage.setResultReason(resultReason);
		CDAPMessageValidator.validate(cdapMessage);
		return cdapMessage;
	}

	public int getAbsSyntax() {
		return absSyntax;
	}

	public void setAbsSyntax(int absSyntax) {
		this.absSyntax = absSyntax;
	}

	public String getAuthMech() {
		return authMech;
	}

	public void setAuthMech(String authMech) {
		this.authMech = authMech;
	}

	public byte[] getAuthValue() {
		return authValue;
	}

	public void setAuthValue(byte[] authValue) {
		this.authValue = authValue;
	}

	public String getDestAEInst() {
		return destAEInst;
	}

	public void setDestAEInst(String destAEInst) {
		this.destAEInst = destAEInst;
	}

	public String getDestAEName() {
		return destAEName;
	}

	public void setDestAEName(String destAEName) {
		this.destAEName = destAEName;
	}

	public String getDestApInst() {
		return destApInst;
	}

	public void setDestApInst(String destApInst) {
		this.destApInst = destApInst;
	}

	public String getDestApName() {
		return destApName;
	}

	public void setDestApName(String destApName) {
		this.destApName = destApName;
	}

	public byte[] getFilter() {
		return filter;
	}

	public void setFilter(byte[] filter) {
		this.filter = filter;
	}

	public Flags[] getFlags() {
		return flags;
	}

	public void setFlags(Flags[] flags) {
		this.flags = flags;
	}

	public int getInvokeID() {
		return invokeID;
	}

	public void setInvokeID(int invokeID) {
		this.invokeID = invokeID;
	}

	public String getObjClass() {
		return objClass;
	}

	public void setObjClass(String objClass) {
		this.objClass = objClass;
	}

	public long getObjInst() {
		return objInst;
	}

	public void setObjInst(long objInst) {
		this.objInst = objInst;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public byte[] getObjValue() {
		return objValue;
	}

	public void setObjValue(byte[] objValue) {
		this.objValue = objValue;
	}

	public Opcode getOpCode() {
		return opCode;
	}

	public void setOpCode(Opcode opCode) {
		this.opCode = opCode;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getResultReason() {
		return resultReason;
	}

	public void setResultReason(String resultReason) {
		this.resultReason = resultReason;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	public String getSrcAEInst() {
		return srcAEInst;
	}

	public void setSrcAEInst(String srcAEInst) {
		this.srcAEInst = srcAEInst;
	}

	public String getSrcAEName() {
		return srcAEName;
	}

	public void setSrcAEName(String srcAEName) {
		this.srcAEName = srcAEName;
	}

	public String getSrcApInst() {
		return srcApInst;
	}

	public void setSrcApInst(String srcApInst) {
		this.srcApInst = srcApInst;
	}

	public String getSrcApName() {
		return srcApName;
	}

	public void setSrcApName(String srcApName) {
		this.srcApName = srcApName;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	public String toString(){
		String result = "\n"+this.getOpCode()+"\n";
		result = result + "abstract syntax: "+ this.getAbsSyntax() + "\n";
		result = result + "authentication mechanism: "+ this.getAuthMech() + "\n";
		result = result + "authentication value: "+ this.getAuthValue() + "\n";
		result = result + "destination AP name: "+ this.getDestApName() + "\n";
		result = result + "destination AP instance: "+ this.getDestApInst() + "\n";
		result = result + "destination AE name: "+ this.getDestAEName() + "\n";
		result = result + "destination AE instance: "+ this.getDestAEInst() + "\n";
		result = result + "Filter: "+ this.getFilter() + "\n";
		result = result + "Flags: "+ this.getFlags() + "\n";
		result = result + "Invoke id: "+ this.getInvokeID() + "\n";
		result = result + "Object class: "+ this.getObjClass()+ "\n";
		result = result + "Object instance: "+ this.getObjInst()+ "\n";
		result = result + "Object value: "+ this.getObjValue()+ "\n";
		result = result + "Result: "+ this.getResult()+ "\n";
		result = result + "Result Reason: "+ this.getResultReason()+ "\n";
		result = result + "Scope: "+ this.getScope()+ "\n";
		result = result + "source AP name: "+ this.getSrcApName() + "\n";
		result = result + "source AP instance: "+ this.getSrcApInst() + "\n";
		result = result + "source AE name: "+ this.getSrcAEName() + "\n";
		result = result + "source AE instance: "+ this.getSrcAEInst() + "\n";
		result = result + "Version: "+ this.getVersion()+ "\n";
		return result;
	}
}