package com.sazonov.mainonlineshop.serviceinterface;

import com.sazonov.mainonlineshop.dto.UserDto;
import com.sazonov.mainonlineshop.userentity.UserEntity;

import java.util.List;

public interface UserService {

    UserDto saveUser (UserDto userDto);

    UserEntity findOneByEmail(String email);

    UserDto findUserByEmail(String email);

    List<UserDto> getAllUsers();

    List<UserDto> findAllByEmail(String email);


    UserDto findById(int id);

    UserDto updateUser(UserDto userDto);

    void deleteUser(UserDto userDto);


}
