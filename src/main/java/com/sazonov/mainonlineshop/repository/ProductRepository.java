package com.sazonov.mainonlineshop.repository;

import com.sazonov.mainonlineshop.shopentity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    ProductEntity findByName(String name);

    ProductEntity findById(int id);

}
