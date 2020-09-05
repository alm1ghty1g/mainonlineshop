package com.sazonov.mainonlineshop.mapper;


import com.sazonov.mainonlineshop.dto.UserDto;
import com.sazonov.mainonlineshop.dto.formDto.UserSingUpDtoRequest;
import com.sazonov.mainonlineshop.exception.UserIsAlreadyExistException;
import com.sazonov.mainonlineshop.repository.CartRepository;
import com.sazonov.mainonlineshop.repository.UserRepository;
import com.sazonov.mainonlineshop.shopentity.CartEntity;
import com.sazonov.mainonlineshop.userentity.Roles;
import com.sazonov.mainonlineshop.userentity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class UserMapper {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ShopMapper shopMapper;


    public Function<UserDto, UserEntity> userToEntity = p -> getUserEntity(p);
    public Function<UserEntity, UserDto> userToDto = p -> getUserDto(p);

    public <A, R> List<R> collectionToList(Collection<A> collection, Function<A, R> mapper) {
        return collection.stream().map(p -> mapper.apply(p)).collect(Collectors.toList());
    }


    public UserDto getUserDtoForSignUp(UserSingUpDtoRequest userSingUpDto) {


        return UserDto.builder()
                .email(userSingUpDto.getEmail())
                .password(userSingUpDto.getPassword())
                .firstName(userSingUpDto.getFirstName())
                .lastName(userSingUpDto.getLastName())
                .address(userSingUpDto.getAddress())
                .phoneNumber(userSingUpDto.getPhoneNumber())
                .created(LocalDate.now())
                .updated(LocalDate.now())
                .lastVisit(LocalDate.now())
                .role(Roles.ROLE_CUSTOMER.name())
                .build();
    }


    public UserEntity getUserEntityForSignUp(UserDto userDto) {

        UserEntity userEntity = userRepository.findByEmail(userDto.getEmail());

        if (userEntity != null) {
            throw new UserIsAlreadyExistException("User is already exist");
        }
        else
        return UserEntity.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .address(userDto.getAddress())
                .phoneNumber(userDto.getPhoneNumber())
                .role(userDto.getRole())
                .created(userDto.getCreated())
                .updated(userDto.getUpdated())
                .lastVisit(userDto.getLastVisit())
                .cartEntity(cartRepository.save(new CartEntity()))
                .active(true)

                .build();
    }


    public UserEntity getUserEntityForUpdate(UserDto userDto) {

        UserEntity userEntity = userRepository.findById(userDto.getId());

        CartEntity cartEntity = userEntity.getCartEntity();

        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setAddress(userDto.getAddress());
        userEntity.setPhoneNumber(userDto.getPhoneNumber());
        userEntity.setCreated(userDto.getCreated());
        userEntity.setUpdated(LocalDate.now());
        userEntity.setLastVisit(userDto.getLastVisit());
        userEntity.setRole(userDto.getRole());
        userEntity.setCartEntity(cartEntity);

        return userEntity;
    }


    public UserEntity getUserEntity(UserDto userDto) {

        return UserEntity.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .address(userDto.getAddress())
                .phoneNumber(userDto.getPhoneNumber())
                .role(userDto.getRole())
                .created(userDto.getCreated())
                .updated(userDto.getUpdated())
                .lastVisit(userDto.getLastVisit())
                .cartEntity(shopMapper.getCartEntity(userDto.getCartDto()))
                .role(userDto.getRole())

                .build();

    }

    public UserDto getUserDto(UserEntity userEntity) {


        return UserDto.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .address(userEntity.getAddress())
                .phoneNumber(userEntity.getPhoneNumber())
                .role(userEntity.getRole())
                .created(userEntity.getCreated())
                .updated(userEntity.getUpdated())
                .lastVisit(userEntity.getLastVisit())
                .cartDto(shopMapper.getCartDto(userEntity.getCartEntity()))
                .role(userEntity.getRole())

                .build();
    }




}
