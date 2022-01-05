/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.bank.client.impl;

import java.util.logging.Level;
import org.solent.com504.oodd.bank.model.client.BankRestClient;
import org.solent.com504.oodd.bank.model.dto.CreditCard;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.logging.LoggingFeature;
import org.solent.com504.oodd.bank.model.dto.TransactionReplyMessage;
import org.solent.com504.oodd.bank.model.dto.TransactionRequestMessage;

/**
 * The implemented version of BankRestClient
 * @author WT000
 */
public class BankRestClientImpl implements BankRestClient {

    final static Logger LOG = LogManager.getLogger(BankRestClientImpl.class);
    final static Logger TRANSACTION_LOGGER = LogManager.getLogger("TRANSACTION_LOGGER");

    String urlStr;

    /**
     *
     * @param urlStr The REST URL to use
     */
    public BankRestClientImpl(String urlStr) {
        this.urlStr = urlStr;
    }

    /**
     * Used to send money without to user auth
     * 
     * @param fromCard The card sending money
     * @param toCard The card getting money
     * @param amount The amount to send
     * @return An object which represents the transaction status
     */
    @Override
    public TransactionReplyMessage transferMoney(CreditCard fromCard, CreditCard toCard, Double amount) {
        LOG.debug("transferMoney called: ");

        // sets up logging for the client       
        Client client = ClientBuilder.newClient(new ClientConfig().register(
                new LoggingFeature(java.util.logging.Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME),
                        Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, 10000)));

        // allows client to decode json
        client.register(JacksonJsonProvider.class);

        WebTarget webTarget = client.target(urlStr).path("/transactionRequest");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        TransactionRequestMessage transactionRequestMessage = new TransactionRequestMessage();
        transactionRequestMessage.setAmount(amount);
        transactionRequestMessage.setFromCard(fromCard);
        transactionRequestMessage.setToCard(toCard);

        Response response = invocationBuilder.post(Entity.entity(transactionRequestMessage, MediaType.APPLICATION_JSON));
        
        TransactionReplyMessage transactionReplyMessage = response.readEntity(TransactionReplyMessage.class);
        
        LOG.debug("Response status=" + response.getStatus() + " ReplyMessage: " + transactionReplyMessage);
        
        // Log to file with a title depending on the transaction
        if (!fromCard.getCardnumber().equals(toCard.getCardnumber())) {
            TRANSACTION_LOGGER.info("TRANSACTION " + transactionReplyMessage.getStatus() + " (to other card): " + transactionReplyMessage);
        } else {
            TRANSACTION_LOGGER.info("TRANSACTION " + transactionReplyMessage.getStatus() + " (to own card): " + transactionReplyMessage);
        }

        return transactionReplyMessage;
    }
    
    /**
     * Used to send money with to user auth
     * 
     * @param fromCard The card sending money
     * @param toCard The card getting money
     * @param amount The amount to send
     * @param userName Username of to card
     * @param password Password of to card
     * @return An object which represents the transaction status
     */
    @Override
    public TransactionReplyMessage transferMoney(CreditCard fromCard, CreditCard toCard, Double amount, String userName, String password) {
        // Note that userName and password are for the account tied to toCard.
        LOG.debug("transferMoney called: ");

        // sets up logging for the client       
        Client client = ClientBuilder.newClient(new ClientConfig().register(
                new LoggingFeature(java.util.logging.Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME),
                        Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, 10000)));

        // basic authentication
        HttpAuthenticationFeature basicAuthfeature = HttpAuthenticationFeature.basic(userName, password);
        client.register(basicAuthfeature);
        
        
        // allows client to decode json
        client.register(JacksonJsonProvider.class);
        WebTarget webTarget = client.target(urlStr).path("/transactionRequest");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        TransactionRequestMessage transactionRequestMessage = new TransactionRequestMessage();
        transactionRequestMessage.setAmount(amount);
        transactionRequestMessage.setFromCard(fromCard);
        transactionRequestMessage.setToCard(toCard);

        Response response = invocationBuilder.post(Entity.entity(transactionRequestMessage, MediaType.APPLICATION_JSON));

        TransactionReplyMessage transactionReplyMessage = response.readEntity(TransactionReplyMessage.class);

        LOG.debug("Response status=" + response.getStatus() + " ReplyMessage: " + transactionReplyMessage);
        
        // Log to file with a title depending on the transaction
        if (!fromCard.getCardnumber().equals(toCard.getCardnumber())) {
            TRANSACTION_LOGGER.info("TRANSACTION " + transactionReplyMessage.getStatus() + " (to other card): " + transactionReplyMessage);
        } else {
            TRANSACTION_LOGGER.info("TRANSACTION " + transactionReplyMessage.getStatus() + " (to own card): " + transactionReplyMessage);
        }

        return transactionReplyMessage;
    }
}
