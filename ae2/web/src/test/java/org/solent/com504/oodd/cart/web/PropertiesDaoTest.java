/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.web;

import java.io.File;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PropertiesDaoTest {

    public final String TEST_PROPERTIES_FILE = "./target/test/propertiesDaoTest.properties";

    @Before
    public void before() {
        // make sue file doesnt exist
        File propertiesFile = new File(TEST_PROPERTIES_FILE);
        if (propertiesFile.exists()) {
            propertiesFile.delete();
        }
    }

    @Test
    public void testPropertiesDao() {
        PropertiesDao propertiesDao = new PropertiesDao(TEST_PROPERTIES_FILE);

        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.testPropertiesFile", TEST_PROPERTIES_FILE);
        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.username", "testUserName");
        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.password", "testPassword");
        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.url", "http://google.com");

        String url = propertiesDao.getProperty("org.solent.oodd.ae2.test.web.url");
        assertEquals("http://google.com", url);

        // Test with different DAO (normally there will only be one dao for the file)
        PropertiesDao propertiesDao2 = new PropertiesDao(TEST_PROPERTIES_FILE);
        String url2 = propertiesDao2.getProperty("org.solent.oodd.ae2.test.web.url");
        assertEquals("http://google.com", url2);
        
        // note that the file will contain org.solent.ood.simplepropertiesdaowebapp.url=http\://google.com
        // with an extra | escape characters. see discussion here
        // https://stackoverflow.com/questions/21711562/java-properties-class-adding-characters-when-url-is-entered
    }
    @Test
    public void testPropertiesUsername() {
        PropertiesDao propertiesDao = new PropertiesDao(TEST_PROPERTIES_FILE);

        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.testPropertiesFile", TEST_PROPERTIES_FILE);
        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.username", "testUserName");
        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.password", "testPassword");
        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.url", "http://google.com");
        
        String Username = propertiesDao.getProperty("org.solent.oodd.ae2.test.web.username");
        assertEquals("testUserName", Username);        
    }
    
    @Test
    public void testPropertiesUpassword() {
        PropertiesDao propertiesDao = new PropertiesDao(TEST_PROPERTIES_FILE);

        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.testPropertiesFile", TEST_PROPERTIES_FILE);
        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.username", "testUserName");
        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.password", "testPassword");
        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.url", "http://google.com");
        
        String Password = propertiesDao.getProperty("org.solent.oodd.ae2.test.web.password");
        assertEquals("testPassword", Password);        
    }
    
    @Test
    public void testPropertiesCardName() {
        PropertiesDao propertiesDao = new PropertiesDao(TEST_PROPERTIES_FILE);

        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.testPropertiesFile", TEST_PROPERTIES_FILE);
        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.cardCvv", "nasi");        
        String cardCVV = propertiesDao.getProperty("org.solent.oodd.ae2.test.web.cardCvv");
        assertEquals("nasi", cardCVV);        
    }
    
    @Test
    public void testPropertiesCardDate() {
        PropertiesDao propertiesDao = new PropertiesDao(TEST_PROPERTIES_FILE);

        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.testPropertiesFile", TEST_PROPERTIES_FILE);
        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.cardDate", "11/10/2021");        
        String cardDate = propertiesDao.getProperty("org.solent.oodd.ae2.test.web.cardDate");
        assertEquals("11/10/2021", cardDate);        
    }
    
    @Test
    public void testPropertiesCardNumber() {
        PropertiesDao propertiesDao = new PropertiesDao(TEST_PROPERTIES_FILE);

        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.testPropertiesFile", TEST_PROPERTIES_FILE);
        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.cardNumber", "123456789");        
        String cardNumber = propertiesDao.getProperty("org.solent.oodd.ae2.test.web.cardNumber");
        assertEquals("123456789", cardNumber);        
    }
    
    @Test
    public void testPropertiesHashPassword() {
        PropertiesDao propertiesDao = new PropertiesDao(TEST_PROPERTIES_FILE);

        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.testPropertiesFile", TEST_PROPERTIES_FILE);
        propertiesDao.setProperty("org.solent.oodd.ae2.test.web.hashedPassword", "$2a$12$gXPR0dBcA0OvlQD2SpJMb.QUoj9xXvdqjQ44Ns1zxxpEJjJoC.Hhu");        
        String hash = propertiesDao.getProperty("org.solent.oodd.ae2.test.web.hashedPassword");
        assertEquals("$2a$12$gXPR0dBcA0OvlQD2SpJMb.QUoj9xXvdqjQ44Ns1zxxpEJjJoC.Hhu", hash);        
    }
}
