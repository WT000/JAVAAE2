/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.web;

import java.io.File;
import org.solent.com504.oodd.cart.service.ShoppingServiceImpl;
import org.solent.com504.oodd.cart.service.ShoppingCartImpl;
import org.solent.com504.oodd.cart.model.service.ShoppingCart;
import org.solent.com504.oodd.cart.model.service.ShoppingService;
import org.solent.com504.oodd.cart.service.ServiceObjectFactory;

/**
 *
 * @author cgallen
 */
public class WebObjectFactory {
    private static PropertiesDao propertiesDao = null;
    static ShoppingService shoppingService = ServiceObjectFactory.getShoppingService();

    // cannot instantiate
    private WebObjectFactory() {

    }

    public static ShoppingService getShoppingService() {
        return shoppingService;
    }

    public static ShoppingCart getNewShoppingCart() {
        return ServiceObjectFactory.getNewShoppingCart();
    }

    /**
     * Used to get the PropertiesDao of the application
     *
     * @return The properties DAO
     */
    public static PropertiesDao getPropertiesDao() {
        if (propertiesDao == null) {
            synchronized (WebObjectFactory.class) {
                if (propertiesDao == null) {
                    // creates a single instance of the dao
                    String TEMP_DIR = System.getProperty("java.io.tmpdir");

                    File propertiesFile = new File(TEMP_DIR + "/application.properties");
                    propertiesDao = new PropertiesDao(propertiesFile.getAbsolutePath());
                }
            }
        }
        return propertiesDao;
    }
}
