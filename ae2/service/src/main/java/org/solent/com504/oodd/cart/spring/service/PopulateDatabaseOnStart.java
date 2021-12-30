/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.spring.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.solent.com504.oodd.cart.dao.impl.UserRepository;
import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.dto.UserRole;
import org.solent.com504.oodd.cart.dao.impl.ShoppingItemCatalogRepository;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.dto.ShoppingItemCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author cgallen
 */
@Component
public class PopulateDatabaseOnStart {

    private static final Logger LOG = LogManager.getLogger(PopulateDatabaseOnStart.class);

    private static final String DEFAULT_ADMIN_USERNAME = "globaladmin";
    private static final String DEFAULT_ADMIN_PASSWORD = "globaladmin";

    private static final String DEFAULT_USER_PASSWORD = "user1234";
    private static final String DEFAULT_USER_USERNAME = "user1234";
    
    private static final String DEFAULT_ITEM_NAME_1 = "Pet Alligator";
    private static final String DEFAULT_ITEM_DESC_1 = "You read that name right!";
    
    private static final String DEFAULT_ITEM_NAME_2 = "Gaming Laptop";
    private static final String DEFAULT_ITEM_DESC_2 = "An i9 core and RTX 3070!";
    
    private static final String DEFAULT_ITEM_NAME_3 = "Mini Telescope";
    private static final String DEFAULT_ITEM_DESC_3 = "Look to the stars!";
    
    private static final String DEFAULT_ITEM_NAME_4 = "PS5";
    private static final String DEFAULT_ITEM_DESC_4 = "The newest gaming console!";

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ShoppingItemCatalogRepository catalogRepository;

    @PostConstruct
    public void initDatabase() {
        LOG.debug("populating database with default users");

        // Populate database with admin and normal user if they don't exist
        User adminUser = new User();
        adminUser.setUsername(DEFAULT_ADMIN_USERNAME);
        adminUser.setFirstName("default administrator");
        adminUser.setPassword(DEFAULT_ADMIN_PASSWORD);
        adminUser.setUserRole(UserRole.ADMINISTRATOR);

        List<User> users = userRepository.findByUsername(DEFAULT_ADMIN_USERNAME);
        if (users.isEmpty()) {
            userRepository.save(adminUser);
            LOG.info("creating new default admin user:" + adminUser);
        } else {
            LOG.info("default admin user already exists. Not creating new :" + adminUser);
        }

        User defaultUser = new User();
        defaultUser.setUsername(DEFAULT_USER_USERNAME);
        defaultUser.setFirstName("default user");
        defaultUser.setPassword(DEFAULT_USER_PASSWORD);
        defaultUser.setUserRole(UserRole.CUSTOMER);

        users = userRepository.findByUsername(DEFAULT_USER_USERNAME);
        if (users.isEmpty()) {
            userRepository.save(defaultUser);
            LOG.info("creating new default user:" + defaultUser);
        } else {
            LOG.info("defaultuser already exists. Not creating new :" + defaultUser);
        }
        
        LOG.debug("populating database with default items");
        
        // Populate database with items if they don't exist
        ShoppingItem petAlligator = new ShoppingItem();
        petAlligator.setCategory(ShoppingItemCategory.OTHER);
        petAlligator.setName(DEFAULT_ITEM_NAME_1);
        petAlligator.setDescription(DEFAULT_ITEM_DESC_1);
        petAlligator.setPrice(50.0);
        petAlligator.setQuantity(2);
        
        List<ShoppingItem> items = catalogRepository.findByNameIgnoreCase(DEFAULT_ITEM_NAME_1);
        
        if (items.isEmpty()) {
            catalogRepository.save(petAlligator);
            LOG.info("creating new default item 1:" + petAlligator);
            List<ShoppingItem> testItems = catalogRepository.findByNameIgnoreCase(DEFAULT_ITEM_NAME_1);
            if (testItems.size() == 1) {
                LOG.info("default item 1 added!");
            }
        } else {
            LOG.info("default item 1 already exists. Not creating new :" + petAlligator);
        }
        
        ShoppingItem gamingLaptop = new ShoppingItem();
        gamingLaptop.setCategory(ShoppingItemCategory.TECH);
        gamingLaptop.setName(DEFAULT_ITEM_NAME_2);
        gamingLaptop.setDescription(DEFAULT_ITEM_DESC_2);
        gamingLaptop.setPrice(700.0);
        gamingLaptop.setQuantity(7);
        
        items = catalogRepository.findByNameIgnoreCase(DEFAULT_ITEM_NAME_2);
        
        if (items.isEmpty()) {
            catalogRepository.save(gamingLaptop);
            LOG.info("creating new default item 2:" + gamingLaptop);
            List<ShoppingItem> testItems = catalogRepository.findByNameIgnoreCase(DEFAULT_ITEM_NAME_2);
            if (testItems.size() == 1) {
                LOG.info("default item 2 added!");
            }
        } else {
            LOG.info("default item 2 already exists. Not creating new :" + gamingLaptop);
        }
        
        ShoppingItem miniTelescope = new ShoppingItem();
        miniTelescope.setCategory(ShoppingItemCategory.ASTRONOMY);
        miniTelescope.setName(DEFAULT_ITEM_NAME_3);
        miniTelescope.setDescription(DEFAULT_ITEM_DESC_3);
        miniTelescope.setPrice(20.0);
        miniTelescope.setQuantity(13);
        
        items = catalogRepository.findByNameIgnoreCase(DEFAULT_ITEM_NAME_3);
        
        if (items.isEmpty()) {
            catalogRepository.save(miniTelescope);
            LOG.info("creating new default item 3:" + miniTelescope);
            List<ShoppingItem> testItems = catalogRepository.findByNameIgnoreCase(DEFAULT_ITEM_NAME_3);
            if (testItems.size() == 1) {
                LOG.info("default item 3 added!");
            }
        } else {
            LOG.info("default item 3 already exists. Not creating new :" + miniTelescope);
        }
        
        ShoppingItem ps5 = new ShoppingItem();
        ps5.setCategory(ShoppingItemCategory.TECH);
        ps5.setName(DEFAULT_ITEM_NAME_4);
        ps5.setDescription(DEFAULT_ITEM_DESC_4);
        ps5.setPrice(500.0);
        ps5.setQuantity(0);
        
        items = catalogRepository.findByNameIgnoreCase(DEFAULT_ITEM_NAME_4);
        
        if (items.isEmpty()) {
            catalogRepository.save(ps5);
            LOG.info("creating new default item 4:" + ps5);
            List<ShoppingItem> testItems = catalogRepository.findByNameIgnoreCase(DEFAULT_ITEM_NAME_4);
            if (testItems.size() == 1) {
                LOG.info("default item 4 added!");
            }
        } else {
            LOG.info("default item 4 already exists. Not creating new :" + ps5);
        }
        
        LOG.debug("database initialised");
    }
}
