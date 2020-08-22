package com.sazonov.mainonlineshop.repository;

import com.sazonov.mainonlineshop.shopentity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
}
