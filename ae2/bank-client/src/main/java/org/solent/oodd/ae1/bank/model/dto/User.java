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
package org.solent.oodd.ae1.bank.model.dto;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import org.solent.oodd.ae1.password.PasswordUtils;

/**
 * User object representation
 * @author Will
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
     * @return First name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName First name to set to
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return Surname
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     *
     * @param secondName Surname to set to
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     *
     * @return Address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address Address to set to
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username Username to set to
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Note that this is never saved anywhere
     * @return Plain text password
     */
    @Transient
    public String getPassword() {
        return password;
    }

    /**
     * Hashed password is stored in a database
     * @param password Password to set to
     */
    public void setPassword(String password) {
        this.password = password;
        setHashedPassword(PasswordUtils.hashPassword(password));
    }

    /**
     *
     * @return Hashed password
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     *
     * @param hashedPassword Hashed password to set to
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    // no password or hashed password
    @Override
    public String toString() {
        return "User{" + "firstName=" + firstName + ", secondName=" + secondName + ", address=" + address + ", username=" + username + '}';
    }

}
