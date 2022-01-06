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
package org.solent.com504.oodd.bank.model.dto;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import org.solent.com504.oodd.password.PasswordUtils;

/**
 * User for bank transactions, it's important to not get this User mixed up with User's for the shopping service!
 * @author WT000
 */
@Embeddable
public class User {

    private String firstName = "";

    private String secondName = "";

    private String address = "";

    private String username = "";

    private String password = "";

    private String hashedPassword = "";

    /**
     *
     * @return Current firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName firstName to set to
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return Current secondName
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     *
     * @param secondName secondName to set to
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     *
     * @return Current address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address address to set to
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return Current username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username username to set to
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Transient, meaning the database doesn't see this
     * @return Current password
     */
    @Transient
    public String getPassword() {
        return password;
    }

    /**
     * Only the hashedPassword is saved in the database
     * @param password password to set to
     */
    public void setPassword(String password) {
        this.password = password;
        setHashedPassword(PasswordUtils.hashPassword(password));
    }

    /**
     *
     * @return Current hashedPassword
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     *
     * @param hashedPassword hashedPassword to set to
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    // No password or hashed password
    @Override
    public String toString() {
        return "User{" + "firstName=" + firstName + ", secondName=" + secondName + ", address=" + address + ", username=" + username + '}';
    }

}
