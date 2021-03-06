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
package org.solent.com504.oodd.bank.model.client;

import org.solent.com504.oodd.bank.model.dto.CreditCard;
import org.solent.com504.oodd.bank.model.dto.TransactionReplyMessage;

/**
 * Interface for ReST client
 * @author WT000
 */
public interface BankRestClient {

    /**
     *
     * @param fromCard The card to take money from
     * @param toCard The card to put money into
     * @param amount The amount to send
     * @return The result of the transaction
     */
    public  TransactionReplyMessage transferMoney(CreditCard fromCard, CreditCard toCard, Double amount);

    /**
     *
     * @param fromCard The card to take money from
     * @param toCard The card to put money into
     * @param amount The amount to send
     * @param userName The toCard username
     * @param password The toCard password
     * @return The result of the transaction
     */
    public  TransactionReplyMessage transferMoney(CreditCard fromCard, CreditCard toCard, Double amount, String userName, String password);
}
