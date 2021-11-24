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

/**
 * Transaction request object representation
 * @author Will
 */
public class TransactionRequestMessage {

    private CreditCard fromCard;

    private CreditCard toCard;

    private Double amount;

    /**
     *
     * @return The from card
     */
    public CreditCard getFromCard() {
        return fromCard;
    }

    /**
     *
     * @param fromCard The from card to set to
     */
    public void setFromCard(CreditCard fromCard) {
        this.fromCard = fromCard;
    }

    /**
     *
     * @return The to card
     */
    public CreditCard getToCard() {
        return toCard;
    }

    /**
     *
     * @param toCard The to card to set to
     */
    public void setToCard(CreditCard toCard) {
        this.toCard = toCard;
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
        return "TransactionRequestMessage{" + "fromCard=" + fromCard + ", toCard=" + toCard + ", amount=" + amount + '}';
    }
    
    
}
