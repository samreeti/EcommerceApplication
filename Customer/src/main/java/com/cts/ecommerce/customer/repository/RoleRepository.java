package com.cts.ecommerce.customer.repository;

import com.cts.ecommerce.customer.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity,Integer> {
}
