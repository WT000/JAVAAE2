/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.model.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class InvoiceItem {

    private Long id;
    private ShoppingItem dbItem;
    private Integer quantity = 0;
    private Double price;
    private String name;
    

    public InvoiceItem() {

    }

    public InvoiceItem(ShoppingItem dbItem, Integer quantity) {
        this.dbItem = dbItem;
        this.quantity = quantity;
        this.price = dbItem.getPrice();
        this.name = dbItem.getName();
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @OneToOne
    public ShoppingItem getDbItem() {
        return dbItem;
    }

    public void setDbItem(ShoppingItem dbItem) {
        this.dbItem = dbItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "InvoiceItem{quantity=" + quantity + ", price=" + price + ", name=" + name + ", dbItem=" + dbItem + '}';
    }

}
