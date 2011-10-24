package rina.cdap.impl.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import rina.cdap.api.CDAPException;
import rina.cdap.api.message.CDAPMessage;
import rina.cdap.api.message.ObjectValue;

public class CreateDeleteTest extends BaseCDAPTest{
	
	@Before
	public void setup(){
		super.setup();
		try{
			connect();
		}catch(CDAPException ex){
			ex.printStackTrace();
		}
	}
	
	@Test
	public void testSingleCreateWithResponse() throws CDAPException{
		CDAPMessage cdapMessage = null;
		byte[] message = null;
		int invokeId = 0;
		
		cdapMessage = cdapSessionManager.getCreateObjectRequestMessage(32768, null, null, "org.pouzinsociety.flow.Flow", 0, "123", null, 0, true);
		invokeId = cdapMessage.getInvokeID();
		message = sendingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		sendingCDAPSession.messageSent(cdapMessage);
		
		receivingCDAPSession.messageReceived(message);
		
		boolean failed = false;
		cdapMessage = cdapSessionManager.getCreateObjectResponseMessage(32769, null, "org.pouzinsociety.flow.Flow", 0, "123", new ObjectValue(), 0, null, invokeId + 1);
		try{
			message = receivingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		}catch(CDAPException ex){
			System.out.println(ex.getMessage());
			failed = true;
		}

		Assert.assertTrue(failed);
		
		cdapMessage = cdapSessionManager.getCreateObjectResponseMessage(32769, null, "org.pouzinsociety.flow.Flow", 0, "123", new ObjectValue(), 0, null, invokeId);
		message = receivingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		receivingCDAPSession.messageSent(cdapMessage);
		
		sendingCDAPSession.messageReceived(message);
	}
	
	@Test
	public void testSingleCreateWithoutResponse() throws CDAPException{
		CDAPMessage cdapMessage = null;
		byte[] message = null;

		cdapMessage = cdapSessionManager.getCreateObjectRequestMessage(32768, null, null, "org.pouzinsociety.flow.Flow", 0, "123", null, 0, false);
		message = sendingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		sendingCDAPSession.messageSent(cdapMessage);

		receivingCDAPSession.messageReceived(message);

		boolean failed = false;
		cdapMessage = cdapSessionManager.getCreateObjectResponseMessage(32769, null, "org.pouzinsociety.flow.Flow", 0, "123", new ObjectValue(), 0, null, 25);
		try{
			message = receivingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		}catch(CDAPException ex){
			System.out.println(ex.getMessage());
			failed = true;
		}

		Assert.assertTrue(failed);
	}
	
	@Test
	public void testMultipleCreateWithResponses() throws CDAPException{
		CDAPMessage cdapMessage = null;
		byte[] message = null;

		cdapMessage = CDAPMessage.getCreateObjectRequestMessage(null, null, 2, "org.pouzinsociety.flow.Flow", 0, "123", null, 0);
		message = sendingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		sendingCDAPSession.messageSent(cdapMessage);
		
		receivingCDAPSession.messageReceived(message);
		
		cdapMessage = CDAPMessage.getCreateObjectRequestMessage(null, null, 3, "org.pouzinsociety.flow.Flow", 0, "976", null, 0);
		message = sendingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		sendingCDAPSession.messageSent(cdapMessage);
		
		receivingCDAPSession.messageReceived(message);
		
		cdapMessage = CDAPMessage.getCreateObjectResponseMessage(null, 2, "org.pouzinsociety.flow.Flow", 0, "123", new ObjectValue(), 0, null);
		message = receivingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		receivingCDAPSession.messageSent(cdapMessage);
		
		sendingCDAPSession.messageReceived(message);
		
		cdapMessage = CDAPMessage.getCreateObjectResponseMessage(null, 3, "org.pouzinsociety.flow.Flow", 0, "976", new ObjectValue(), 0, null);
		message = receivingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		receivingCDAPSession.messageSent(cdapMessage);
		
		sendingCDAPSession.messageReceived(message);
	}
	
	@Test
	public void testCreateDisconnected() throws CDAPException{
		this.disconnectWithoutResponse();
		CDAPMessage cdapMessage = null;

		boolean failed = false;
		cdapMessage = CDAPMessage.getCreateObjectRequestMessage(null, null, 2, "org.pouzinsociety.flow.Flow", 0, "123", null, 0);
		try{
			sendingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		}catch(CDAPException ex){
			System.out.println(ex.getMessage());
			failed = true;
		}

		Assert.assertTrue(failed);
	}
	
	@Test
	public void testSingleDeleteWithResponse() throws CDAPException{
		CDAPMessage cdapMessage = null;
		byte[] message = null;
		
		cdapMessage = CDAPMessage.getDeleteObjectRequestMessage(null, null, 25, "org.pouzinsociety.flow.Flow", 0, "123", 0);
		message = sendingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		sendingCDAPSession.messageSent(cdapMessage);
		
		receivingCDAPSession.messageReceived(message);
		
		boolean failed = false;
		cdapMessage = CDAPMessage.getCreateObjectResponseMessage(null, 25, "org.pouzinsociety.flow.Flow", 0, "123", new ObjectValue(), 0, null);
		try{
			message = receivingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		}catch(CDAPException ex){
			System.out.println(ex.getMessage());
			failed = true;
		}

		Assert.assertTrue(failed);
		
		cdapMessage = CDAPMessage.getDeleteObjectResponseMessage(null, 25, "org.pouzinsociety.flow.Flow", 0, "123", 0, null);
		message = receivingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		receivingCDAPSession.messageSent(cdapMessage);
		
		sendingCDAPSession.messageReceived(message);
	}
	
	@Test
	public void testSingleDeleteWithoutResponse() throws CDAPException{
		CDAPMessage cdapMessage = null;
		byte[] message = null;

		cdapMessage = CDAPMessage.getDeleteObjectRequestMessage(null, null, 0, "org.pouzinsociety.flow.Flow", 0, "123", 0);
		message = sendingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		sendingCDAPSession.messageSent(cdapMessage);

		receivingCDAPSession.messageReceived(message);

		boolean failed = false;
		cdapMessage = CDAPMessage.getDeleteObjectResponseMessage(null, 25, "org.pouzinsociety.flow.Flow", 0, "123", 0, null);
		try{
			message = receivingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		}catch(CDAPException ex){
			System.out.println(ex.getMessage());
			failed = true;
		}

		Assert.assertTrue(failed);
	}
	
	@Test
	public void testMultipleDeleteWithResponses() throws CDAPException{
		CDAPMessage cdapMessage = null;
		byte[] message = null;

		cdapMessage = CDAPMessage.getDeleteObjectRequestMessage(null, null, 2, "org.pouzinsociety.flow.Flow", 0, "123", 0);
		message = sendingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		sendingCDAPSession.messageSent(cdapMessage);
		
		receivingCDAPSession.messageReceived(message);
		
		cdapMessage = CDAPMessage.getDeleteObjectRequestMessage(null, null, 3, "org.pouzinsociety.flow.Flow", 0, "789", 0);
		message = sendingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		sendingCDAPSession.messageSent(cdapMessage);
		
		receivingCDAPSession.messageReceived(message);
		
		cdapMessage = CDAPMessage.getDeleteObjectResponseMessage(null, 2, "org.pouzinsociety.flow.Flow", 0, "123", 0, null);
		message = receivingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		receivingCDAPSession.messageSent(cdapMessage);
		
		sendingCDAPSession.messageReceived(message);
		
		cdapMessage = CDAPMessage.getDeleteObjectResponseMessage(null, 3, "org.pouzinsociety.flow.Flow", 0, "789", 0, null);
		message = receivingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		receivingCDAPSession.messageSent(cdapMessage);
		
		sendingCDAPSession.messageReceived(message);
	}
	
	@Test
	public void testDeleteDisconnected() throws CDAPException{
		this.disconnectWithoutResponse();
		CDAPMessage cdapMessage = null;

		boolean failed = false;
		cdapMessage = CDAPMessage.getDeleteObjectRequestMessage(null, null, 2, "org.pouzinsociety.flow.Flow", 0, "123", 0);
		try{
			sendingCDAPSession.encodeNextMessageToBeSent(cdapMessage);
		}catch(CDAPException ex){
			System.out.println(ex.getMessage());
			failed = true;
		}

		Assert.assertTrue(failed);
	}
}
