package com.sazonov.mainonlineshop.repository;

import com.sazonov.mainonlineshop.shopentity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    ProductEntity findByName(String name);

    Set<ProductEntity> findAllByNameContains(String name);

    ProductEntity findById(int id);

}
