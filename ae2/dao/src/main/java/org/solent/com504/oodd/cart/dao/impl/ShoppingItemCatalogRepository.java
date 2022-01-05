package org.solent.com504.oodd.cart.dao.impl;

import java.util.List;
import org.solent.com504.oodd.cart.model.dto.ShoppingItemCategory;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingItemCatalogRepository  extends JpaRepository<ShoppingItem,Long>{
    // The query below is used to test
    public List<ShoppingItem> findByNameIgnoreCase(String name);
    
    public List<ShoppingItem> findByUuid(String uuid);
    
    @Query("select si from ShoppingItem si where UPPER(si.name) like UPPER(?1) order by si.quantity desc, si.name")
    public List<ShoppingItem> findByNameIgnoreCaseContaining(String name);
    
    @Query("select si from ShoppingItem si where si.category = ?1 order by si.quantity desc, si.name")
    public List<ShoppingItem> findByCategory(ShoppingItemCategory category);
    
    @Query("select si from ShoppingItem si where UPPER(si.name) like UPPER(?1) and si.category = ?2 order by si.quantity desc, si.name")
    public List<ShoppingItem> findByNameIgnoreCaseContainingAndCategory(String name, ShoppingItemCategory category);
    
    @Query("select si from ShoppingItem si order by si.quantity desc, si.name")
    public List<ShoppingItem> findAllItems();
    
    public Long deleteByUuid(String uuid);
    
    @Query("select si from ShoppingItem si where si.quantity > 0 order by si.id desc")
    public List<ShoppingItem> findAvailableItems();
    
    @Query("select si from ShoppingItem si where si.quantity = 0 order by si.id desc")
    public List<ShoppingItem> findUnavailableItems();
}
