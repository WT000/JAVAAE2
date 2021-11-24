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
package org.solent.oodd.ae1.bank.model.client;

import org.solent.oodd.ae1.bank.model.dto.CreditCard;
import org.solent.oodd.ae1.bank.model.dto.TransactionReplyMessage;

/**
 * Rest Client interface which is implemented in other classes
 * @author Will
 */
public interface BankRestClient {

    /**
     *
     * @param fromCard The card to get money from
     * @param toCard The card to send the money to
     * @param amount The amount to send
     * @return An object which represents the transaction status
     */
    public  TransactionReplyMessage transferMoney(CreditCard fromCard, CreditCard toCard, Double amount);

    /**
     * Note that the username and password are for the to account
     * 
     * @param fromCard The card to get money from
     * @param toCard The card to send the money to
     * @param amount The amount to send
     * @param userName The username of the to card
     * @param password The password of the to card
     * @return An object which represents the transaction status
     */
    public  TransactionReplyMessage transferMoney(CreditCard fromCard, CreditCard toCard, Double amount, String userName, String password);
}
