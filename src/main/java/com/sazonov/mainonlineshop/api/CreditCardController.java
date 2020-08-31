package com.sazonov.mainonlineshop.api;

import com.sazonov.mainonlineshop.dto.CreditCardDto;
import com.sazonov.mainonlineshop.dto.formDto.AddCardDtoRequest;
import com.sazonov.mainonlineshop.mapper.CreditCardMapper;
import com.sazonov.mainonlineshop.serviceinterface.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController("/card")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private CreditCardMapper creditCardMapper;


    @PostMapping("/addCard")
    public ResponseEntity<CreditCardDto> saveCard(@RequestBody AddCardDtoRequest addCardDtoRequest) {

        CreditCardDto creditCardDto = creditCardMapper.getCreditCardDtoToAddCard(addCardDtoRequest);

        return ResponseEntity.ok(creditCardService.saveCard(creditCardDto));

    }

    @GetMapping("/deleteCard/{id}") //TEST
    public ResponseEntity<CreditCardDto> delete(@PathVariable("id") int id) {

        creditCardService.deleteCard(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllCards")
    public ResponseEntity<List<CreditCardDto>> getAllCards() {

        return ResponseEntity.ok(creditCardService.getAllCards());

    }

}
