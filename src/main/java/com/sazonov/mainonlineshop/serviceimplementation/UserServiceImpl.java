package com.sazonov.mainonlineshop.serviceimplementation;

import com.sazonov.mainonlineshop.dto.UserDto;
import com.sazonov.mainonlineshop.mapper.UserMapper;
import com.sazonov.mainonlineshop.repository.CartRepository;
import com.sazonov.mainonlineshop.repository.UserRepository;
import com.sazonov.mainonlineshop.serviceinterface.UserService;
import com.sazonov.mainonlineshop.userentity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;


    public UserDto saveUser(UserDto userDto) {


        UserEntity userEntity = userMapper.getUserEntityForSignUp(userDto);

        userRepository.save(userEntity);

        return userMapper.getUserDto(userEntity);
    }



    public UserEntity findOneByEmail(String email) {

        return userRepository.findByEmail(email);
    }


    public List<UserDto> getAllUsers() {

        List<UserEntity> userEntityList = userRepository.findAll();

        return userMapper.collectionToList(userEntityList, userMapper.userToDto);
    }



    public UserDto findById(int id) {

        UserEntity userEntity = userRepository.findById(id);

        return userMapper.getUserDto(userEntity);
    }


    public List<UserDto> findByEmail(String email) {

        List<UserEntity> userEntityList = userRepository.findByEmailContains(email);

        return userMapper.collectionToList(userEntityList, userMapper.userToDto);

    }

    public UserDto updateUser(UserDto userDto) {

        UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserEntity userEntity = userMapper.getUserEntityForUpdate(userDetails.getName(), userDto);

        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(userEntity);

        return userMapper.getUserDto(userEntity);
    }


    public void deleteUser(UserDto userDto) {

        UserEntity userEntity = userRepository.findById(userDto.getId());
        userRepository.delete(userEntity);
        cartRepository.delete(userEntity.getCartEntity());

    }
}
