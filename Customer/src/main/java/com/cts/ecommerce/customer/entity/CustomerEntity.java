package com.cts.ecommerce.customer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@Entity
@Table(name= "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private Integer customer_id;
    @NotEmpty(message = "Required filed")
    @Length(min=3, message=" CustomerName should have min 3 letters ")
    private String customerName;
    @Length(min=5, message = " password should have at least 5 characters ")
    @JsonIgnore
    private String password;
    @NotNull(message = "Required filed")
    private String deliveryAddress;
    @NotNull(message = "Required filed")
    @Past(message = " Date of Birth must be less then today ")
    private LocalDate dateOfBirth;
}
