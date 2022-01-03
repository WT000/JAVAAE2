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
    // Uncomment the various lines of code if you want the link between items
    // to be saved! It's off by default, as I want the invoice to be saved
    // even if the items within it are deleted.
    
    private Long id;
    private String uuid;
    private Integer quantity = 0;
    private Double price;
    private String name;
//    private ShoppingItem dbItem;

    public InvoiceItem() {

    }

    public InvoiceItem(ShoppingItem dbItem, Integer quantity) {
        this.uuid = dbItem.getUuid();
        this.quantity = quantity;
        this.price = dbItem.getPrice();
        this.name = dbItem.getName();
//        this.dbItem = dbItem;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
//    @OneToOne
//    public ShoppingItem getDbItem() {
//        return dbItem;
//    }
//
//    public void setDbItem(ShoppingItem dbItem) {
//        this.dbItem = dbItem;
//    }

    @Override
    public String toString() {
        return "InvoiceItem{uuid=" + uuid + ", quantity=" + quantity + ", price=" + price + ", name=" + name + '}';
    }

}
