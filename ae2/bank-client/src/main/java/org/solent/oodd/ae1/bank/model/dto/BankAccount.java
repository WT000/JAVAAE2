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

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Bank Account object representation
 * @author Will
 */
@Entity
public class BankAccount {

    private Long id;
    
    private User owner = new User();

    private String sortcode="";

    private String accountNo="";
    
    private Double balance = 0.0;

    private CreditCard creditcard = new CreditCard();

    private boolean active = true;

    /**
     *
     * @return The account ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id The ID to set to
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return The owner (user object)
     */
    @Embedded
    public User getOwner() {
        return owner;
    }

    /**
     *
     * @param owner The owner (user object) of the account
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     *
     * @return The Sortcode of the account
     */
    public String getSortcode() {
        return sortcode;
    }

    /**
     *
     * @param sortcode The Sortcode to set to
     */
    public void setSortcode(String sortcode) {
        this.sortcode = sortcode;
    }

    /**
     *
     * @return The account number for this account
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     *
     * @param accountNo The account number to set to
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     *
     * @return The credit card (object) of this account
     */
    @Embedded
    public CreditCard getCreditcard() {
        return creditcard;
    }

    /**
     *
     * @param creditcard The credit card object to set to
     */
    public void setCreditcard(CreditCard creditcard) {
        this.creditcard = creditcard;
    }

    /**
     *
     * @return The status of the account
     */
    public boolean isActive() {
        return active;
    }

    /**
     *
     * @param active The status to set to
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     *
     * @return The balance of the account
     */
    public Double getBalance() {
        return balance;
    }

    /**
     *
     * @param balance The balance to set to
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BankAccount{" + "id=" + id + ", owner=" + owner + ", sortcode=" + sortcode + ", accountNo=" + accountNo + ", balance=" + balance + ", creditcard=" + creditcard + ", active=" + active + '}';
    }
}
