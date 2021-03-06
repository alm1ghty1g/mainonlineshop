package com.sazonov.mainonlineshop.dto.formdto;

import lombok.*;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


public class AddProductDtoRequest {

    private String name;
    private double price;
    private LocalDate expirationDate;
    private String category;

}
