/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.oodd.ae1.bank.model.dto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
        
        //instance.setEndDate("12/99");
        //LOG.debug("Testing card with valid short endDate: " + instance.getEndDate());
        //assertEquals(instance.cardDateExpiredOrError(), false);
        
        //instance.setEndDate("12/2020");
        //LOG.debug("Testing card with expired endDate: " + instance.getEndDate());
        //assertEquals(instance.cardDateExpiredOrError(), true);
        
        //instance.setEndDate("12/2999");
        //LOG.debug("Testing valid boundary endDate: " + instance.getEndDate());
        //assertEquals(instance.cardDateExpiredOrError(), false);
        
        //instance.setEndDate("12/3000");
        //LOG.debug("Testing valid boundary endDate: " + instance.getEndDate());
        //assertEquals(instance.cardDateExpiredOrError(), false);
    }
}
