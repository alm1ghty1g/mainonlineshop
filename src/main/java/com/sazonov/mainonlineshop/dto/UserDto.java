package com.sazonov.mainonlineshop.dto;


import lombok.*;


import java.time.LocalDate;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString



public class UserDto {


    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String role;
    private LocalDate created;
    private LocalDate updated;
    private LocalDate lastVisit;

    private CartDto cartDto;


}
