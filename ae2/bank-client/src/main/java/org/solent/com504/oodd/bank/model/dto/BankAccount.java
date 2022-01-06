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

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * BankAccount representation
 * @author WT000
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
     * @return ID Get Account ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return Owner Get Owner (User)
     */
    @Embedded
    public User getOwner() {
        return owner;
    }

    /**
     *
     * @param owner User to set
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     *
     * @return Get Sortcode
     */
    public String getSortcode() {
        return sortcode;
    }

    /**
     *
     * @param sortcode Sortcode to set
     */
    public void setSortcode(String sortcode) {
        this.sortcode = sortcode;
    }

    /**
     *
     * @return Get Account number
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     *
     * @param accountNo accountNo to set to
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     *
     * @return Get CreditCard
     */
    @Embedded
    public CreditCard getCreditcard() {
        return creditcard;
    }

    /**
     *
     * @param creditcard CreditCard to set to
     */
    public void setCreditcard(CreditCard creditcard) {
        this.creditcard = creditcard;
    }

    /**
     *
     * @return Get Active boolean
     */
    public boolean isActive() {
        return active;
    }

    /**
     *
     * @param active boolean to set to
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     *
     * @return Get account balance
     */
    public Double getBalance() {
        return balance;
    }

    /**
     *
     * @param balance Balance to set to
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BankAccount{" + "id=" + id + ", owner=" + owner + ", sortcode=" + sortcode + ", accountNo=" + accountNo + ", balance=" + balance + ", creditcard=" + creditcard + ", active=" + active + '}';
    }


    
    
    
}
