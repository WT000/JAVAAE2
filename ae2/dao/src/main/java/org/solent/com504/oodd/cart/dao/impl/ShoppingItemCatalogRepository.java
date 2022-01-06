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
package org.solent.com504.oodd.cart.dao.impl;

import java.util.List;
import org.solent.com504.oodd.cart.model.dto.ShoppingItemCategory;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Shopping Item database
 * @author WT000
 */
@Repository
public interface ShoppingItemCatalogRepository  extends JpaRepository<ShoppingItem,Long>{
    /**
     * (testing query)
     * @param name The item name to search for
     * @return A list of ShoppingItem's which match the criteria
     */
    public List<ShoppingItem> findByNameIgnoreCase(String name);
    
    /**
     *
     * @param uuid The item UUID to search for
     * @return A list of ShoppingItem's which match the criteria
     */
    public List<ShoppingItem> findByUuid(String uuid);
    
    /**
     *
     * @param name The item name to search for
     * @return A list of ShoppingItem's which match the criteria
     */
    @Query("select si from ShoppingItem si where UPPER(si.name) like UPPER(?1) order by si.quantity desc, si.name")
    public List<ShoppingItem> findByNameIgnoreCaseContaining(String name);
    
    /**
     *
     * @param category The category to search for
     * @return A list of ShoppingItem's which match the criteria
     */
    @Query("select si from ShoppingItem si where si.category = ?1 order by si.quantity desc, si.name")
    public List<ShoppingItem> findByCategory(ShoppingItemCategory category);
    
    /**
     *
     * @param name The name to search for
     * @param category The category to search for
     * @return A list of ShoppingItem's which match the criteria
     */
    @Query("select si from ShoppingItem si where UPPER(si.name) like UPPER(?1) and si.category = ?2 order by si.quantity desc, si.name")
    public List<ShoppingItem> findByNameIgnoreCaseContainingAndCategory(String name, ShoppingItemCategory category);
    
    /**
     *
     * @return A list of every item in the database
     */
    @Query("select si from ShoppingItem si order by si.quantity desc, si.name")
    public List<ShoppingItem> findAllItems();
    
    /**
     *
     * @param uuid The UUID to search for and delete
     */
    public void deleteByUuid(String uuid);
    
    /**
     *
     * @return A list of ShoppingItem's where their quantity is greater than 0
     */
    @Query("select si from ShoppingItem si where si.quantity > 0 order by si.id desc")
    public List<ShoppingItem> findAvailableItems();
    
    /**
     *
     * @return A list of ShoppingItem's where their quantity is 0
     */
    @Query("select si from ShoppingItem si where si.quantity = 0 order by si.id desc")
    public List<ShoppingItem> findUnavailableItems();
}
