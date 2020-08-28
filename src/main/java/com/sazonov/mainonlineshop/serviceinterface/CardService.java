package com.sazonov.mainonlineshop.serviceinterface;

import com.sazonov.mainonlineshop.dto.CardDto;

import java.util.List;

public interface CardService {

    CardDto saveCard(CardDto cardDto);

    void deleteCard(int id);

    List<CardDto> getAllCards();

}
