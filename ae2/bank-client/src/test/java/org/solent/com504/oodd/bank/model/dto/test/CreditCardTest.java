/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.bank.model.dto.test;


import org.solent.com504.oodd.bank.model.dto.CreditCard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Will
 */
public class CreditCardTest {

    final static Logger LOG = LogManager.getLogger(CreditCardTest.class);
    
    /**
     * Test of cardDateExpiredOrError method, of class CreditCard.
     */
    @Test
    public void testCardDateExpiredOrError() {
        CreditCard instance = new CreditCard();
        
        LOG.debug("Testing card without endDate: " + instance.getEndDate());
        assertEquals(instance.cardDateExpiredOrError(), true);
        
        instance.setEndDate("aw98eu89auwf");
        LOG.debug("Testing card with bad endDate: " + instance.getEndDate());
        assertEquals(instance.cardDateExpiredOrError(), true);
        
        instance.setEndDate("12/99");
        LOG.debug("Testing card with valid short endDate: " + instance.getEndDate());
        assertEquals(instance.cardDateExpiredOrError(), false);
    }
}
