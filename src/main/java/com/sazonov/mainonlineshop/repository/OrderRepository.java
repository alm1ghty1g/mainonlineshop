package com.sazonov.mainonlineshop.repository;

import com.sazonov.mainonlineshop.shopentity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {


}
