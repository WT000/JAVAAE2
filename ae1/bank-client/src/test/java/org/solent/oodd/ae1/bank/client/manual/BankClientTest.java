/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.oodd.ae1.bank.client.manual;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.solent.oodd.ae1.bank.client.impl.BankRestClientImpl;
import org.solent.oodd.ae1.bank.model.client.BankRestClient;
import org.solent.oodd.ae1.bank.model.dto.BankTransactionStatus;
import org.solent.oodd.ae1.bank.model.dto.CreditCard;
import org.solent.oodd.ae1.bank.model.dto.TransactionReplyMessage;

/**
 *
 * @author WT000
 * @nastaransharifisadr
 */
public class BankClientTest {

    final static Logger LOG = LogManager.getLogger(BankClientTest.class);
    
    // Test against the real rest service instead of the local application
    String bankUrl = "http://com528bank.ukwest.cloudapp.azure.com:8080/rest";
    CreditCard fromCard = null;
    CreditCard toCard = null;
    
    String toUsername=null;
    String toPassword=null;

    @Before
    public void before() {
        fromCard = new CreditCard();
        fromCard.setCardnumber("5133880000000012");
        fromCard.setCvv("123");
        fromCard.setEndDate("11/21");
        fromCard.setIssueNumber("01"); 
        fromCard.setName("test user1");

        toCard = new CreditCard();
        toCard.setCardnumber("4285860000000021");
        toCard.setCvv("123");
        toCard.setEndDate("11/21");
        toCard.setIssueNumber("01");
        toCard.setName("test user2");
        
        toUsername = "testuser2";
        toPassword = "defaulttestpass";
    }

    @Test
    public void testClient() {
        BankRestClient client = new BankRestClientImpl(bankUrl);

        Double amount = 0.0;

        TransactionReplyMessage reply = client.transferMoney(fromCard, toCard, amount);
        LOG.debug("transaction reply:" + reply);

        assertEquals(BankTransactionStatus.SUCCESS, reply.getStatus());
    }
    //added by nastaran
    @Test
    public void testCVV() {
        BankRestClient client = new BankRestClientImpl(bankUrl);
        assertEquals(fromCard.getCvv(), "123");
    }
    //added by nastaran
    @Test
    public void testEndDate() {
        BankRestClient client = new BankRestClientImpl(bankUrl);
        assertEquals(fromCard.getEndDate(), "11/21");
    }
    //added by nastaran
    @Test
    public void testIssueNumber() {
        BankRestClient client = new BankRestClientImpl(bankUrl);
        assertEquals(fromCard.getIssueNumber(), "01");
    }
    
    //added by nastaran
    @Test
    public void testName() {
        BankRestClient client = new BankRestClientImpl(bankUrl);
        assertEquals(fromCard.getName(), "test user1");
    }
    //added by nastaran
    @Test
    public void testtransfermoney() {
        BankRestClient client = new BankRestClientImpl(bankUrl);
    
        double amount = Double.parseDouble("1.0");
        
        // First get the money from the bank account (if someone from another group has changed it)
        client.transferMoney(toCard, fromCard, amount);
        
        // Then, now that we know the balance is in the account, we can send the transfer
        TransactionReplyMessage reply = client.transferMoney(fromCard, toCard, amount);
        
        // Send it back into the appropriate account
        client.transferMoney(toCard, fromCard, amount);
       
        LOG.debug("transaction reply:" + reply);

        assertEquals("1.0",reply.getAmount().toString());
    }
    

    @Test
    public void testClientAuth() {

        BankRestClient client = new BankRestClientImpl(bankUrl);

        Double amount = 0.0;

        // testing with auth
        TransactionReplyMessage reply = client.transferMoney(fromCard, toCard, amount, toUsername, toPassword);
        LOG.debug("transaction with auth reply:" + reply);
        
        assertEquals(BankTransactionStatus.SUCCESS, reply.getStatus());
    }
}
