package com.sazonov.mainonlineshop.dto;

import lombok.*;


import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class ProductDto {

    private int id;

    private String name;

    private double price;

    private LocalDate expirationDate;

    private String category;
}
