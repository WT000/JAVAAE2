/*
 * Copyright 2022 WT000
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.solent.com504.oodd.cart.model.dto.InvoiceTest;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

import org.solent.com504.oodd.cart.model.dto.Invoice;
import org.solent.com504.oodd.cart.model.dto.InvoiceItem;
import org.solent.com504.oodd.cart.model.dto.InvoiceStatus;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;

/**
 *
 * @author Will
 */
public class InvoiceTest {
    final static Logger LOG = LogManager.getLogger(InvoiceTest.class);
    
    @Test
    public void testCreateEditInvoice() {
        Invoice testInvoice = new Invoice();
        
        LOG.debug("attempting to set invoice status to all major statuses");
        assertEquals(InvoiceStatus.PROCESSING, testInvoice.getCurrentStatus());
        
        testInvoice.setCurrentStatus(InvoiceStatus.SHIPPING);
        assertEquals(InvoiceStatus.SHIPPING, testInvoice.getCurrentStatus());
        
        testInvoice.setCurrentStatus(InvoiceStatus.DELIVERING);
        assertEquals(InvoiceStatus.DELIVERING, testInvoice.getCurrentStatus());
        
        testInvoice.setCurrentStatus(InvoiceStatus.REFUNDED);
        assertEquals(InvoiceStatus.REFUNDED, testInvoice.getCurrentStatus());
        
        LOG.debug("creating an InvoiceItem and adding it to the invoice");
        ShoppingItem testItem = new ShoppingItem();
        testItem.setName("Crocodile");
        testItem.setPrice(5.0);
        
        // 1 is the quantity of the item
        InvoiceItem testInvoiceItem = new InvoiceItem(testItem, 1);
        assertEquals("Crocodile", testInvoiceItem.getName());
        assertEquals((Double) 5.0, testInvoiceItem.getPrice());
        assertEquals((Integer) 1, testInvoiceItem.getQuantity());
        
        // Ensure the InvoiceItem name is saved, it shouldn't change from how
        // they purchased it
        testItem.setName("Elephant");
        testItem.setPrice(10.0);
        
        assertEquals("Crocodile", testInvoiceItem.getName());
        assertEquals((Double) 5.0, testInvoiceItem.getPrice());
        assertEquals((Integer) 1, testInvoiceItem.getQuantity());
        
        // Add the item to the Invoice and attempt to get it back
        ArrayList<InvoiceItem> boughtItems = new ArrayList<InvoiceItem>();
        boughtItems.add(testInvoiceItem);
        
        testInvoice.setSavedBasketItems(boughtItems);
        assertEquals(1, testInvoice.getSavedBasketItems().size());
        assertEquals("Crocodile", testInvoice.getSavedBasketItems().get(0).getName());
    }
}
