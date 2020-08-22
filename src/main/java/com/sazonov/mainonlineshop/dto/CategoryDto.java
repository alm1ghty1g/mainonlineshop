package com.sazonov.mainonlineshop.dto;

import lombok.*;


import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class CategoryDto {

    private String name;
    private Set<ProductDto> productDtoSet;

}
