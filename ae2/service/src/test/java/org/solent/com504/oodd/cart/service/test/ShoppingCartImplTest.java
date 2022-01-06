/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.service.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

import org.solent.com504.oodd.cart.service.ShoppingCartImpl;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;

/**
 *
 * @author Will
 */
public class ShoppingCartImplTest {
    final static Logger LOG = LogManager.getLogger(ShoppingCartImplTest.class);
    
    @Test
    public void testCreateEditInvoice() {
        // The basket should only hold UUID references to the ShoppingItem's
        // and the quantity of them
        ShoppingCartImpl basketTest = new ShoppingCartImpl();
        
        ShoppingItem testItem = new ShoppingItem();
        testItem.setUuid("test");
        
        basketTest.getBasket().put(testItem.getUuid(), 1);
        
        assertEquals((Integer) 1, basketTest.getBasket().get("test"));
    }
}
