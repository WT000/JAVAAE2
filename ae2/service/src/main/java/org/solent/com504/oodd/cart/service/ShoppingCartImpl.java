/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
