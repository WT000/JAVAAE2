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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * InvoiceItem stored within an Invoice object
 * @author Will
 */
@Entity
public class InvoiceItem {
    // Uncomment the various lines of code if you want the link between items
    // to be saved! It's off by default, as I want the invoice to be saved
    // even if the items within it are deleted.
    // Ensure you import javax.persistence.OneToOne; if you do.
    
    private Long id;
    private String uuid;
    private Integer quantity = 0;
    private Double price;
    private String name;
//    private ShoppingItem dbItem;

    /**
     * Blank constructor
     */
    public InvoiceItem() {

    }

    /**
     *
     * @param dbItem The ShoppingItem to take attributes from
     * @param quantity The quantity of the item which were purchased
     */
    public InvoiceItem(ShoppingItem dbItem, Integer quantity) {
        this.uuid = dbItem.getUuid();
        this.quantity = quantity;
        this.price = dbItem.getPrice();
        this.name = dbItem.getName();
//        this.dbItem = dbItem;
    }

    /**
     *
     * @return Current id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id id to set to
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     *
     * @return Current quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity quantity to set to
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return Current price
     */
    public Double getPrice() {
        return price;
    }

    /**
     *
     * @param price price to set to
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     *
     * @return Current name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name name to set to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return Current uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     *
     * @param uuid uuid to set to
     */
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
