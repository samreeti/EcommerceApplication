package com.cts.ecommerce.customer.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name= "role")
public class RoleEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer role_id;

}
