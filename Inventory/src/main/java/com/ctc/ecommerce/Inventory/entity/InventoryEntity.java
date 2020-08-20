package com.ctc.ecommerce.Inventory.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name= "Inventory")
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    @NotNull
    private String productName;
    @NotNull
    private Integer quantity;
    @NotNull
    private Long pricePerUnit;
    @NotNull
    private String dateAdded;

}
