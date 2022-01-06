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
 * Different categories of items, defaulted to OTHER when made by the MVC
 * @author WT000
 */
public enum ShoppingItemCategory {

    /**
     * Technology (e.g. laptops)
     */
    TECH,

    /**
     * Home (e.g. furniture)
     */
    HOME,

    /**
     * Do It Yourself (e.g. hammers)
     */
    DIY,

    /**
     * Toys (e.g. figures)
     */
    TOYS,

    /**
     * Clothing (e.g. t-shirts)
     */
    CLOTHING,

    /**
     * Sports (e.g. weights)
     */
    SPORTS,

    /**
     * Jewelry (e.g. rings)
     */
    JEWELRY,

    /**
     * Astronomy (e.g. telescopes)
     */
    ASTRONOMY,

    /**
     * Other (anything that isn't a category)
     */
    OTHER;
}
