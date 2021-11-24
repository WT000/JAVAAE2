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

import java.util.Date;

/**
 * Transaction Reply object representation
 * @author Will
 */
public class TransactionReplyMessage {

    private Integer code;

    private String message;

    private BankTransactionStatus status;

    private String fromCardNo;

    private String toCardNo;

    private String transactionId;

    private Date transactionDate;
    
    private Double amount;

    /**
     *
     * @return The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     *
     * @param code The code to set to
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     *
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message The message to set to
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return The status
     */
    public BankTransactionStatus getStatus() {
        return status;
    }

    /**
     *
     * @param status The status to set to
     */
    public void setStatus(BankTransactionStatus status) {
        this.status = status;
    }

    /**
     *
     * @return The from card
     */
    public String getFromCardNo() {
        return fromCardNo;
    }

    /**
     *
     * @param fromCardNo The from card to set to
     */
    public void setFromCardNo(String fromCardNo) {
        this.fromCardNo = fromCardNo;
    }

    /**
     *
     * @return The to card
     */
    public String getToCardNo() {
        return toCardNo;
    }

    /**
     *
     * @param toCardNo The to card to set to
     */
    public void setToCardNo(String toCardNo) {
        this.toCardNo = toCardNo;
    }

    /**
     *
     * @return The transaction ID
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     *
     * @param transactionId The transaction ID to set to
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     *
     * @return The transaction date
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     *
     * @param transactionDate The transaction date to set to
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     *
     * @return The amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     *
     * @param amount The amount to set to
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransactionReplyMessage{" + "code=" + code + ", message=" + message + ", status=" + status + ", fromCardNo=" + fromCardNo + ", toCardNo=" + toCardNo + ", transactionId=" + transactionId + ", transactionDate=" + transactionDate + ", amount=" + amount + '}';
    }


    
    
    
}
