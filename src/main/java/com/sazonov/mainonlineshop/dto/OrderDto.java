package com.sazonov.mainonlineshop.dto;


import lombok.*;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


public class OrderDto {

    private int id;

    private List<LineItemDto> lineItemDtoSet;

    private double orderPrice;

    private UserDto userDto;

    private LocalDate created;

}
