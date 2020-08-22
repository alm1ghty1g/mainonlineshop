package com.sazonov.mainonlineshop.repository;

import com.sazonov.mainonlineshop.shopentity.LineItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineItemRepository extends JpaRepository<LineItemEntity, Integer> {

}
