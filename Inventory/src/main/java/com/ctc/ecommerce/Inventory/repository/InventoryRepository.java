package com.ctc.ecommerce.Inventory.repository;

import com.ctc.ecommerce.Inventory.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Integer> {
    public InventoryEntity findById(int id);
    public InventoryEntity findByProductName(String productName);
}
