/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.model.service;

import java.util.LinkedHashMap;

public interface ShoppingCart {
    public LinkedHashMap<String, Integer> getBasket();
    
    public void setBasket(LinkedHashMap<String, Integer> basket);
}
