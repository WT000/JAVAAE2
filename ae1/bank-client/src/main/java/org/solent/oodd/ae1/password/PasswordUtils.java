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
package org.solent.oodd.ae1.password;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Used to hash or compare strings to hashed
 * @author gallenc
 */
public class PasswordUtils {
    
    /**
     *
     * Performs hashing on a given password
     * @param password The password to hash
     * @return The hashed password
     */
    public static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
    
    /**
     *
     * Checks a hash against plain text
     * @param password The password to compare to
     * @param hashed The hashed password
     * @return A boolean which returns True if matching
     */
    public static boolean checkPassword(String password, String hashed){
        return BCrypt.checkpw(password, hashed);
    }
    
}
