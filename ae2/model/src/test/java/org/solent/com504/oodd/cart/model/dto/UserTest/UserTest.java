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
package org.solent.com504.oodd.cart.model.dto.UserTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.dto.UserRole;
import org.solent.com504.oodd.bank.model.dto.CreditCard;
import org.solent.com504.oodd.cart.model.dto.Address;
import org.solent.com504.oodd.password.PasswordUtils;

/**
 *
 * @author Will
 */
public class UserTest {
    final static Logger LOG = LogManager.getLogger(UserTest.class);

    @Test
    public void testCreateEditUser() {
        User testUser = new User();
        
        LOG.debug("attempting to set user status to all major roles");
        testUser.setUserRole(UserRole.CUSTOMER);
        assertEquals(UserRole.CUSTOMER, testUser.getUserRole());
        
        testUser.setUserRole(UserRole.ADMINISTRATOR);
        assertEquals(UserRole.ADMINISTRATOR, testUser.getUserRole());
        
        testUser.setUserRole(UserRole.ANONYMOUS);
        assertEquals(UserRole.ANONYMOUS, testUser.getUserRole());
        
        LOG.debug("attempting to add a card and address to the user");
        CreditCard userCard = new CreditCard("1111222233334444");
        testUser.setCard(userCard);
        
        Address userAddress = new Address();
        userAddress.setCity("London");
        testUser.setAddress(userAddress);
        
        assertEquals("1111222233334444", testUser.getCard().getCardnumber());
        assertEquals("London", testUser.getAddress().getCity());
        
        LOG.debug("attempting to deactivate the user");
        testUser.setEnabled(false);
        assertFalse(testUser.getEnabled());
        
        LOG.debug("attempting to add password to the user");
        testUser.setPassword("password123");
        assertTrue(PasswordUtils.checkPassword("password123", testUser.getHashedPassword()));
        assertFalse(PasswordUtils.checkPassword("password122", testUser.getHashedPassword()));
    }
}

