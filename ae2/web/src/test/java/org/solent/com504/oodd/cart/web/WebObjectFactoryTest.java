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
package org.solent.com504.oodd.cart.web;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cgallen
 */
public class WebObjectFactoryTest {

    @Test
    public void testGetPropertiesDao() {
        PropertiesDao propertiesDao = WebObjectFactory.getPropertiesDao();

    }

    @After
    public void after() {
        // remove properties file after test
        try {
            String TEMP_DIR = System.getProperty("java.io.tmpdir");
            File propertiesFile = new File(TEMP_DIR + "/application.properties");
            System.out.println("deleting test properties file:"+propertiesFile.getAbsolutePath());
            propertiesFile.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
