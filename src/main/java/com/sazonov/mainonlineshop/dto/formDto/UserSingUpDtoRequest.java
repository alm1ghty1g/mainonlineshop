package com.sazonov.mainonlineshop.dto.formDto;


import lombok.*;

@Data


public class UserSingUpDtoRequest {



    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String address;

    private String phoneNumber;

    private String role;
}
