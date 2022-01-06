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

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;

/**
 * Invoice entity to be stored in the Invoice database
 * @author WT000
 */
@Entity
public class Invoice {

    private Long id;
    private String invoiceNumber;
    private String paymentCardNumber;
    private Date dateOfPurchase;
    private Double amountDue;
    private User purchaser;
    private List<InvoiceItem> savedBasketItems;
    private InvoiceStatus currentStatus = InvoiceStatus.PROCESSING;

    /**
     *
     * @return Current ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    
    /**
     *
     * @param id ID to set to
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return Current invoiceNumber
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     *
     * @param invoiceNumber invoiceNumber to set to
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     *
     * @return Current dateOfPurchase
     */
    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    /**
     *
     * @param dateOfPurchase dateOfPurchase to set to
     */
    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    /**
     *
     * @return Current amountDue
     */
    public Double getAmountDue() {
        return amountDue;
    }

    /**
     *
     * @param amountDue amountDue to set to
     */
    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }
    
    /**
     * Set the currently saved basket items, cascade updates
     * @return Current savedBasketItems
     */
    @OneToMany(cascade = CascadeType.PERSIST) 
    public List<InvoiceItem> getSavedBasketItems() {
        return savedBasketItems;
    }
    
    /**
     *
     * @param basketItems savedBasketItems to set to
     */
    public void setSavedBasketItems(List<InvoiceItem> basketItems) {
        this.savedBasketItems = basketItems;
    }

    /**
     * One to one relationship with a User
     * @return Current purchaser
     */
    @OneToOne
    public User getPurchaser() {
        return purchaser;
    }

    /**
     *
     * @param purchaser purchaser to set to
     */
    public void setPurchaser(User purchaser) {
        this.purchaser = purchaser;
    }

    /**
     *
     * @return Current currentStatus
     */
    public InvoiceStatus getCurrentStatus() {
        return currentStatus;
    }

    /**
     *
     * @param currentStatus currentStatus to set to
     */
    public void setCurrentStatus(InvoiceStatus currentStatus) {
        this.currentStatus = currentStatus;
    }
    
    /**
     *
     * @return Current paymentCardNumber
     */
    public String getPaymentCardNumber() {
        return paymentCardNumber;
    }

    /**
     *
     * @param paymentCardNumber paymentCardNumber to set to
     */
    public void setPaymentCardNumber(String paymentCardNumber) {
        this.paymentCardNumber = paymentCardNumber;
    }
    
    @Override
    public String toString() {
        return "Invoice{invoiceNumber=" + invoiceNumber + ", paymentCardNumber=" + paymentCardNumber + ", amountDue=" + amountDue + ", items=" + savedBasketItems + '}';
    }
}
