package com.cts.ecommerce.customer.dto;

import lombok.Data;

@Data
public class InventoryDTO {

    private int Id;
    private String productName;
    private Integer quantity;
    private Long pricePerUnit;
    private String dateAdded;
}
