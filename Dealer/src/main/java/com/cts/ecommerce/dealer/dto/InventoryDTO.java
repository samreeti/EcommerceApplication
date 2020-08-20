package com.cts.ecommerce.dealer.dto;

import lombok.Data;

@Data
public class InventoryDTO {

    private int Id;
    private String productName;
    private Integer quantity;
    private Long pricePerUnit;
    private String dateAdded;
}
