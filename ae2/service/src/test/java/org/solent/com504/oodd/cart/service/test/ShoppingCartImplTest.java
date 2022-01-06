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
