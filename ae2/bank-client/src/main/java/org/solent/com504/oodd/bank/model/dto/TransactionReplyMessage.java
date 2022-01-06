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

import java.util.Date;

/**
 * The reply message from ReST interactions with the bank
 * @author WT000
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
     * @return Current code
     */
    public Integer getCode() {
        return code;
    }

    /**
     *
     * @param code Code to set to
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     *
     * @return current message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message Message to set to
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return Current status
     */
    public BankTransactionStatus getStatus() {
        return status;
    }

    /**
     *
     * @param status Status to set to
     */
    public void setStatus(BankTransactionStatus status) {
        this.status = status;
    }

    /**
     *
     * @return Current fromCardNo
     */
    public String getFromCardNo() {
        return fromCardNo;
    }

    /**
     *
     * @param fromCardNo fromCardNo to set to
     */
    public void setFromCardNo(String fromCardNo) {
        this.fromCardNo = fromCardNo;
    }

    /**
     *
     * @return Current toCardNo
     */
    public String getToCardNo() {
        return toCardNo;
    }

    /**
     *
     * @param toCardNo toCardNo to set to
     */
    public void setToCardNo(String toCardNo) {
        this.toCardNo = toCardNo;
    }

    /**
     *
     * @return Current transactionId
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     *
     * @param transactionId transactionId to set to
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     *
     * @return Current transactionDate
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     *
     * @param transactionDate transactionDate to set to
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     *
     * @return Current amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     *
     * @param amount amount to set to
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransactionReplyMessage{" + "code=" + code + ", message=" + message + ", status=" + status + ", fromCardNo=" + fromCardNo + ", toCardNo=" + toCardNo + ", transactionId=" + transactionId + ", transactionDate=" + transactionDate + ", amount=" + amount + '}';
    }


    
    
    
}
