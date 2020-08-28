package com.sazonov.mainonlineshop.repository;

import com.sazonov.mainonlineshop.userentity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardEntity, String> {

    CardEntity findById(int id);

}
