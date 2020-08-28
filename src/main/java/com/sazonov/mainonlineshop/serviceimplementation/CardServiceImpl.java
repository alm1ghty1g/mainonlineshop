package com.sazonov.mainonlineshop.serviceimplementation;

import com.sazonov.mainonlineshop.dto.CardDto;
import com.sazonov.mainonlineshop.mapper.CardMapper;
import com.sazonov.mainonlineshop.repository.CardRepository;
import com.sazonov.mainonlineshop.repository.UserRepository;
import com.sazonov.mainonlineshop.serviceinterface.CardService;
import com.sazonov.mainonlineshop.userentity.CardEntity;
import com.sazonov.mainonlineshop.userentity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private CardRepository cardRepository;


    public CardDto saveCard(CardDto cardDto) {


        CardEntity cardEntity = cardMapper.getCardEntity(cardDto);

        cardRepository.save(cardEntity);

        return cardMapper.getCardDto(cardEntity);
    }


    public void deleteCard(int id) {

        CardEntity cardEntity = cardRepository.findById(id);

        cardRepository.delete(cardEntity);

    }


    public List<CardDto> getAllCards() {

        List<CardEntity> cardEntityList = cardRepository.findAll();

        return cardMapper.collectionToList(cardEntityList, cardMapper.cardToDto);
    }
}
