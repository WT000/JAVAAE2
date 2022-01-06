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
