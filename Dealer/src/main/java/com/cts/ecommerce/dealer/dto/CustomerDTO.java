package com.cts.ecommerce.dealer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CustomerDTO {

    private Integer customer_id;
    private String customerName;
    @JsonIgnore
    private String password;
    private String deliveryAddress;
    private LocalDate dateOfBirth;

}
