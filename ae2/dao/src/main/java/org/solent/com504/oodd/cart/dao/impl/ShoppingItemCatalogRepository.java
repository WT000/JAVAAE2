package org.solent.com504.oodd.cart.dao.impl;

import java.util.List;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingItemCatalogRepository  extends JpaRepository<ShoppingItem,Long>{
    public List<ShoppingItem> findByNameIgnoreCase(String name);
}
