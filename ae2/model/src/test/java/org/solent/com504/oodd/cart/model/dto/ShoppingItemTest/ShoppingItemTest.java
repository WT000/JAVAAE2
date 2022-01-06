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
package org.solent.com504.oodd.cart.model.dto.ShoppingItemTest;

import java.util.UUID;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;

/**
 *
 * @author Will
 */
public class ShoppingItemTest {
    final static Logger LOG = LogManager.getLogger(ShoppingItemTest.class);

    @Test
    public void testShoppingItemLookup() {
        // Finding a shopping item in an ArrayList data structure
        ArrayList<ShoppingItem> basketList = new ArrayList<ShoppingItem>();
        
        ShoppingItem shoppingItem1 = new ShoppingItem();
        shoppingItem1.setName("item 1");
        shoppingItem1.setPrice(100.1);
        shoppingItem1.setQuantity(1);
        shoppingItem1.setUuid(UUID.randomUUID().toString());
        
        ShoppingItem shoppingItem2 = new ShoppingItem();
        shoppingItem2.setName("item 2");
        shoppingItem2.setPrice(50.0);
        shoppingItem2.setQuantity(4);
        shoppingItem2.setUuid(UUID.randomUUID().toString());
        
        ShoppingItem shoppingItem3 = new ShoppingItem();
        shoppingItem3.setName("item 3");
        shoppingItem3.setPrice(50.0);
        shoppingItem3.setQuantity(4);
        shoppingItem3.setUuid(UUID.randomUUID().toString());
        
        basketList.add(shoppingItem1);
        basketList.add(shoppingItem2);
        basketList.add(shoppingItem3);
        
        String uuidToFind = basketList.get(2).getUuid();
        LOG.debug("attemping to find " + uuidToFind + " in the list basket.");
        
        boolean found = false;
        for (ShoppingItem item : basketList){
            if (uuidToFind.equals(item.getUuid())) {
                LOG.debug("found the item.");
                found = true;
                break;
            }
        }
        assertTrue(found);
        
        // Finding a shopping item in a HashMap data structure
        LinkedHashMap<String, ShoppingItem> basketTable = new LinkedHashMap<String, ShoppingItem>();
        
        basketTable.put(shoppingItem1.getUuid(), shoppingItem1);
        basketTable.put(shoppingItem2.getUuid(), shoppingItem2);
        basketTable.put(shoppingItem3.getUuid(), shoppingItem3);
        
        LOG.debug("attemping to find " + uuidToFind + " in the dict basket.");
        ShoppingItem foundItem = basketTable.get(uuidToFind);
        
        found = false;
        if (foundItem != null) {
            found = true;
        }
        
        assertTrue(found);
    }
}
