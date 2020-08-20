package com.cts.ecommerce.dealer.repository;

import com.cts.ecommerce.dealer.entity.DealerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealerRepository extends JpaRepository<DealerEntity, Integer> {

}
