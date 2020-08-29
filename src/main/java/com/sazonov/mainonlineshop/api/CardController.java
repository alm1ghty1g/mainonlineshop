package com.sazonov.mainonlineshop.api;

import com.sazonov.mainonlineshop.dto.CardDto;
import com.sazonov.mainonlineshop.dto.formDto.AddCardDtoRequest;
import com.sazonov.mainonlineshop.mapper.CardMapper;
import com.sazonov.mainonlineshop.serviceinterface.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController("/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private CardMapper cardMapper;


    @PostMapping("/addCard")
    public ResponseEntity<CardDto> saveCard(@RequestBody AddCardDtoRequest addCardDtoRequest) {

        CardDto cardDto = cardMapper.getCardDtoToAddCard(addCardDtoRequest);

        return ResponseEntity.ok(cardService.saveCard(cardDto));

    }

    @GetMapping("/deleteCard/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {

        cardService.deleteCard(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllCards")
    public ResponseEntity<List<CardDto>> getAllCards() {

        return ResponseEntity.ok(cardService.getAllCards());

    }

}
