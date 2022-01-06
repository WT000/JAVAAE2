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

/**
 * Represents the status of an order
 * @author Will
 */
public enum InvoiceStatus {

    /**
     * The order has just been made, payment was successful
     */
    PROCESSING,

    /**
     * The contents of the order are being shipped
     */
    SHIPPING,

    /**
     * The contents of the order are on their way
     */
    DELIVERING,

    /**
     * The contents of the order have been delivered
     */
    COMPLETE,

    /**
     * The order was refunded (total amount given back to customer card)
     */
    REFUNDED;
}
