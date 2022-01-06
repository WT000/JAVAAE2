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
package org.solent.com504.oodd.cart.model.service;

import java.util.LinkedHashMap;

/**
 * A representation of a ShoppingCart, which is just a LinkedHashMap
 * @author WT000
 */
public interface ShoppingCart {

    /**
     *
     * @return Current basket
     */
    public LinkedHashMap<String, Integer> getBasket();
    
    /**
     *
     * @param basket basket to set to
     */
    public void setBasket(LinkedHashMap<String, Integer> basket);
}
