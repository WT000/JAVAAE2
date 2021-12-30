package org.solent.com504.oodd.cart.dao.impl;

import java.util.List;
import org.solent.com504.oodd.cart.model.dto.ShoppingItemCategory;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingItemCatalogRepository  extends JpaRepository<ShoppingItem,Long>{
    public List<ShoppingItem> findByNameIgnoreCase(String name);
    
    public List<ShoppingItem> findByNameIgnoreCaseContaining(String name);
    
    public List<ShoppingItem> findByUuid(String Uuid);
    
    public List<ShoppingItem> findByCategory(ShoppingItemCategory category);
    
    public List<ShoppingItem> findByNameIgnoreCaseContainingAndCategory(String name, ShoppingItemCategory category);
    
    @Query("select si from ShoppingItem si where si.quantity > 0")
    public List<ShoppingItem> findAvailableItems();
    
    @Query("select si from ShoppingItem si where si.quantity = 0")
    public List<ShoppingItem> findUnavailableItems();
}
