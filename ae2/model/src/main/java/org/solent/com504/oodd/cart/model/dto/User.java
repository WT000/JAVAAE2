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
package org.solent.com504.oodd.cart.model.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Embedded;
import org.solent.com504.oodd.password.PasswordUtils;
import org.solent.com504.oodd.bank.model.dto.CreditCard;

/**
 * User for the shopping service, it's important to not get this User mixed up with User's for the bank transactions!
 * @author WT000
 */
@Entity
public class User {

    private Long id;

    private String firstName = "";

    private String secondName = "";

    private String username = "";

    private String password = "";

    private String hashedPassword = "";

    private Address address;

    // Optionally store parts of their CreditCard (not cvv)
    private CreditCard card;

    private UserRole userRole;

    private Boolean enabled = true;

    /**
     *
     * @return Current id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id id to set to
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return Current userRole
     */
    public UserRole getUserRole() {
        return userRole;
    }

    /**
     *
     * @param userRole userRole to set to
     */
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
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
     *
     * @return Current address
     */
    @Embedded
    public Address getAddress() {
        return address;
    }

    /**
     *
     * @param address address to set to
     */
    public void setAddress(Address address) {
        this.address = address;
    }

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
     * @return Current enabled boolean
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     *
     * @param enabled enabled boolean to set to
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     *
     * @return Current card
     */
    @Embedded
    public CreditCard getCard() {
        return card;
    }

    /**
     *
     * @param card card to set to
     */
    public void setCard(CreditCard card) {
        this.card = card;
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
     * @param checkPassword The password to check
     * @return true if it's the current password, else false
     */
    public boolean isValidPassword(String checkPassword) {
        if (checkPassword == null || getHashedPassword() == null) {
            return false;
        }
        return PasswordUtils.checkPassword(checkPassword, getHashedPassword());
    }

    /**
     *
     * @return current hashedPassword
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
        return "User{" + "id=" + id + ", firstName=" + firstName + ", secondName=" + secondName + ", username=" + username + ", password=NOT SHOWN, address=" + address + ", userRole=" + userRole + '}';
    }

}
