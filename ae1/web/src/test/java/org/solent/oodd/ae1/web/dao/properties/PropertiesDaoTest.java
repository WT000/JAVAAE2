/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.oodd.ae1.web.dao.properties;

import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nastaransharifisadr
 */
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

        propertiesDao.setProperty("org.solent.oodd.ae1.web.testPropertiesFile", TEST_PROPERTIES_FILE);
        propertiesDao.setProperty("org.solent.oodd.ae1.web.username", "testUserName");
        propertiesDao.setProperty("org.solent.oodd.ae1.web.password", "testPassword");
        propertiesDao.setProperty("org.solent.oodd.ae1.web.url", "http://google.com");

        String url = propertiesDao.getProperty("org.solent.oodd.ae1.web.url");
        assertEquals("http://google.com", url);

        // test with different dao (normally there wiill only be one dao for the file)
        PropertiesDao propertiesDao2 = new PropertiesDao(TEST_PROPERTIES_FILE);
        String url2 = propertiesDao2.getProperty("org.solent.oodd.ae1.web.url");
        assertEquals("http://google.com", url2);
        
        // note that the file will contain org.solent.ood.simplepropertiesdaowebapp.url=http\://google.com
        // with an extra | escape characters. see discussion here
        // https://stackoverflow.com/questions/21711562/java-properties-class-adding-characters-when-url-is-entered
    }
    @Test
    public void testPropertiesUsername() {
        PropertiesDao propertiesDao = new PropertiesDao(TEST_PROPERTIES_FILE);

        propertiesDao.setProperty("org.solent.oodd.ae1.web.testPropertiesFile", TEST_PROPERTIES_FILE);
        propertiesDao.setProperty("org.solent.oodd.ae1.web.username", "testUserName");
        propertiesDao.setProperty("org.solent.oodd.ae1.web.password", "testPassword");
        propertiesDao.setProperty("org.solent.oodd.ae1.web.url", "http://google.com");
        
        String Username = propertiesDao.getProperty("org.solent.oodd.ae1.web.username");
        assertEquals("testUserName", Username);        
    }
    @Test
    public void testPropertiesUpassword() {
        PropertiesDao propertiesDao = new PropertiesDao(TEST_PROPERTIES_FILE);

        propertiesDao.setProperty("org.solent.oodd.ae1.web.testPropertiesFile", TEST_PROPERTIES_FILE);
        propertiesDao.setProperty("org.solent.oodd.ae1.web.username", "testUserName");
        propertiesDao.setProperty("org.solent.oodd.ae1.web.password", "testPassword");
        propertiesDao.setProperty("org.solent.oodd.ae1.web.url", "http://google.com");
        
        String Password = propertiesDao.getProperty("org.solent.oodd.ae1.web.password");
        assertEquals("testPassword", Password);        
    }
    //added by nastaran
    @Test
    public void testPropertiesCardName() {
        PropertiesDao propertiesDao = new PropertiesDao(TEST_PROPERTIES_FILE);

        propertiesDao.setProperty("org.solent.oodd.ae1.web.testPropertiesFile", TEST_PROPERTIES_FILE);
        propertiesDao.setProperty("org.solent.oodd.ae1.web.cardCvv", "nasi");        
        String cardCVV = propertiesDao.getProperty("org.solent.oodd.ae1.web.cardCvv");
        assertEquals("nasi", cardCVV);        
    }
    //added by nastaran
    @Test
    public void testPropertiesCardDate() {
        PropertiesDao propertiesDao = new PropertiesDao(TEST_PROPERTIES_FILE);

        propertiesDao.setProperty("org.solent.oodd.ae1.web.testPropertiesFile", TEST_PROPERTIES_FILE);
        propertiesDao.setProperty("org.solent.oodd.ae1.web.cardDate", "11/10/2021");        
        String cardDate = propertiesDao.getProperty("org.solent.oodd.ae1.web.cardDate");
        assertEquals("11/10/2021", cardDate);        
    }
    //added by nastaran
    @Test
    public void testPropertiesCardNumber() {
        PropertiesDao propertiesDao = new PropertiesDao(TEST_PROPERTIES_FILE);

        propertiesDao.setProperty("org.solent.oodd.ae1.web.testPropertiesFile", TEST_PROPERTIES_FILE);
        propertiesDao.setProperty("org.solent.oodd.ae1.web.cardNumber", "123456789");        
        String cardNumber = propertiesDao.getProperty("org.solent.oodd.ae1.web.cardNumber");
        assertEquals("123456789", cardNumber);        
    }
    //added by nastaran
    @Test
    public void testPropertiesHashPassword() {
        PropertiesDao propertiesDao = new PropertiesDao(TEST_PROPERTIES_FILE);

        propertiesDao.setProperty("org.solent.oodd.ae1.web.testPropertiesFile", TEST_PROPERTIES_FILE);
        propertiesDao.setProperty("org.solent.oodd.ae1.web.hashedPassword", "$2a$12$gXPR0dBcA0OvlQD2SpJMb.QUoj9xXvdqjQ44Ns1zxxpEJjJoC.Hhu");        
        String hash = propertiesDao.getProperty("org.solent.oodd.ae1.web.hashedPassword");
        assertEquals("$2a$12$gXPR0dBcA0OvlQD2SpJMb.QUoj9xXvdqjQ44Ns1zxxpEJjJoC.Hhu", hash);        
    }
    
    
}
