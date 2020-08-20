package com.cts.ecommerce.dealer.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name= "dealer")
public class DealerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer dealer_id;
    @NotNull(message = "Required filed")
    @Length(min=3, message=" DealerName should have min 3 letters ")
    private String dealerName;
    @NotNull(message = "Required filed")
    @Length(min=5, message = " password should have at least 5 characters ")
    private String password;
    @NotNull(message = "Required filed")
    private String storeAddress;


}

