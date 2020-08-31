package com.sazonov.mainonlineshop.serviceimplementation;

import com.sazonov.mainonlineshop.dto.CreditCardDto;
import com.sazonov.mainonlineshop.mapper.CreditCardMapper;
import com.sazonov.mainonlineshop.repository.CreditCardRepository;
import com.sazonov.mainonlineshop.repository.UserRepository;
import com.sazonov.mainonlineshop.serviceinterface.CreditCardService;
import com.sazonov.mainonlineshop.userentity.CreditCardEntity;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditCardMapper creditCardMapper;

    @Autowired
    private CreditCardRepository creditCardRepository;


    public CreditCardDto saveCard(CreditCardDto creditCardDto) {

        CreditCardEntity creditCardEntity = creditCardMapper.getCreditCardEntity(creditCardDto);

        creditCardRepository.save(creditCardEntity);

        return creditCardMapper.getCreditCardDto(creditCardEntity);
    }


    public void deleteCard(int id) {

        CreditCardEntity creditCardEntity = creditCardRepository.findById(id);

        creditCardRepository.delete(creditCardEntity);

    }


    public List<CreditCardDto> getAllCards() {

        List<CreditCardEntity> creditCardEntityList = creditCardRepository.findAll();

        return creditCardMapper.collectionToList(creditCardEntityList, creditCardMapper.cardToDto);
    }
}
