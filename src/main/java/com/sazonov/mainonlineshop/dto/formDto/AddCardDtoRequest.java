package com.sazonov.mainonlineshop.dto.formDto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class AddCardDtoRequest {

    private String cardNumber;

    private String expirationDate;

    private String cardType;
}
