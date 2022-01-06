/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

