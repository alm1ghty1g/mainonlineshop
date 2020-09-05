package com.sazonov.mainonlineshop.repository;

import com.sazonov.mainonlineshop.userentity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByEmail(String email);

    UserEntity findById(int id);

    List<UserEntity> findByEmailContains(String email);


}
