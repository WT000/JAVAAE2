/*
 * Copyright 2021 Will.
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
package org.solent.oodd.ae1.web.dao.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Arrays;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.solent.oodd.ae1.password.PasswordUtils;

/**
 * Used to load, save and delete properties to a properties file
 * @author WT000
 */
public class PropertiesDao {

    final static Logger LOG = LogManager.getLogger(PropertiesDao.class);

    private File propertiesFile;

    private Properties properties = new Properties();

    /**
     *
     * @param propertiesFileLocation The file location to load from
     */
    public PropertiesDao(String propertiesFileLocation) {
        try {
            boolean firstLoad = false;
            propertiesFile = new File(propertiesFileLocation);
            
            if (!propertiesFile.exists()) {
                LOG.info("properties file does not exist: creating new file: " + propertiesFile.getAbsolutePath());
                propertiesFile.getParentFile().mkdirs();
                propertiesFile.createNewFile();
                saveProperties();
                firstLoad = true;
            } 
            loadProperties(firstLoad);
        } catch (IOException ex) {
            LOG.error("cannot load properties", ex);
        }
    }
    
    // synchronized ensures changes are not made by another thread while reading

    /**
     * Used to get properties in a key / value format
     * 
     * @param propertyKey The property key to retrieve
     * @return The value tied to the property
     */
    public synchronized String getProperty(String propertyKey) {
        return properties.getProperty(propertyKey);
    }
    
    /**
     * Used to set properties in a key / value format
     * 
     * @param propertyKey The property key
     * @param propertyValue The property value
     */
    public synchronized void setProperty(String propertyKey, String propertyValue) {
        // Passwords are allowed to be stored in plain text, but this demonstrates hashing the password
        if (propertyKey.equals("org.solent.oodd.ae1.web.password")) {
            String propertyValueToStore = PasswordUtils.hashPassword(propertyValue);
            properties.setProperty("org.solent.oodd.ae1.web.hashedPassword", propertyValueToStore);
        }
        
        properties.setProperty(propertyKey, propertyValue);
        saveProperties();
    }

    /**
     * Used to save a set property to the file
     */
    private void saveProperties() {
        OutputStream output = null;
        try {
            LOG.debug("saving properties to: " + propertiesFile.getAbsolutePath());

            output = new FileOutputStream(propertiesFile);
            String comments = "# saleapp settings file";
            properties.store(output, comments);

        } catch (IOException ex) {
            LOG.error("cannot save properties", ex);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Used to load written properties
     * 
     * @param firstLoad A boolean which determines if the default properties needs to be read
     */
    private void loadProperties(boolean firstLoad) {
        InputStream input = null;
        try {
            // If the file hasn't been made, we need to get the data from the default file, load it into properties and save the file
            if (firstLoad) {
                LOG.info("loading default properties file...");
                input = PropertiesDao.class.getClassLoader().getResourceAsStream("application.properties");
                properties.load(input);
                saveProperties();
            // If it has been created, simply just load it 
            } else {
                LOG.debug("loading properties from: " + propertiesFile.getAbsolutePath());
                input = new FileInputStream(propertiesFile);
                properties.load(input);
            }
        } catch (IOException ex) {
            LOG.error("cannot load properties", ex);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
            }
        }
    }
}
