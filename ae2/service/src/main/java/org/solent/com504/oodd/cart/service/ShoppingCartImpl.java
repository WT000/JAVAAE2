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
package org.solent.com504.oodd.cart.service;

import java.util.LinkedHashMap;
import org.solent.com504.oodd.cart.model.service.ShoppingCart;


public class ShoppingCartImpl implements ShoppingCart {

    private LinkedHashMap<String, Integer> basket = new LinkedHashMap<String, Integer>();
    
    @Override
    public LinkedHashMap<String, Integer> getBasket() {
        return basket;
    }
    
    @Override
    public void setBasket(LinkedHashMap<String, Integer> basket) {
        this.basket = basket;
    }
}
