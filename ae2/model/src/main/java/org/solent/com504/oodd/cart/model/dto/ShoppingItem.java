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
 * Represents an item which can be purchased
 * @author WT000
 */
@Entity
public class ShoppingItem {

    private Long id;
    private String uuid = null;
    private String name = null;
    private String description = null;
    private Integer quantity = 0;
    private Double price = 0.0;

    private ShoppingItemCategory category;

    /**
     * Blank constructor
     */
    public ShoppingItem() {

    }

    /**
     *
     * @param name The name of the item
     * @param price The price of an individual item
     */
    public ShoppingItem(String name, Double price) {
        this.name = name;
        this.price = price;
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
     * @return Current category
     */
    public ShoppingItemCategory getCategory() {
        return category;
    }

    /**
     *
     * @param category category to set to
     */
    public void setCategory(ShoppingItemCategory category) {
        this.category = category;
    }

    /**
     *
     * @return Current description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description description to set to
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ShoppingItem{uuuid=" + uuid + ", name=" + name + ", quantity=" + quantity + ", price=" + price + ", category=" + category + ", description=" + description + '}';
    }

}
