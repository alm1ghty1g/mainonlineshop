package com.sazonov.mainonlineshop.mapper;

import com.sazonov.mainonlineshop.dto.CardDto;
import com.sazonov.mainonlineshop.dto.UserDto;
import com.sazonov.mainonlineshop.dto.formDto.AddCardDtoRequest;
import com.sazonov.mainonlineshop.repository.UserRepository;
import com.sazonov.mainonlineshop.userentity.CardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CardMapper {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;


    public Function<CardDto, CardEntity> cardToEntity = p -> getCardEntity(p);
    public Function<CardEntity, CardDto> cardToDto = p -> getCardDto(p);

    public <A, R> List<R> collectionToList(Collection<A> collection, Function<A, R> mapper) {
        return collection.stream().map(p -> mapper.apply(p)).collect(Collectors.toList());
    }



    public CardDto getCardDtoToAddCard(AddCardDtoRequest addCardDtoRequest) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDto userDto = userMapper.getUserDto(userRepository.findByEmail(auth.getName()));

        return CardDto.builder()
                .cardNumber(addCardDtoRequest.getCardNumber())
                .expirationDate(addCardDtoRequest.getExpirationDate())
                .cardType(addCardDtoRequest.getCardType())
                .userDto(userDto)
                .build();

    }

    public CardDto getCardDto(CardEntity cardEntity) {

        return CardDto.builder()
                .id(cardEntity.getId())
                .cardNumber(cardEntity.getCardNumber())
                .expirationDate(cardEntity.getExpirationDate())
                .cardType(cardEntity.getCardType())
                .userDto(userMapper.getUserDto(cardEntity.getUserEntity()))

                .build();
    }

    public CardEntity getCardEntity(CardDto cardDto) {

        return CardEntity.builder()
                .id(cardDto.getId())
                .cardNumber(cardDto.getCardNumber())
                .expirationDate(cardDto.getExpirationDate())
                .cardType(cardDto.getCardType())
                .userEntity(userMapper.getUserEntity(cardDto.getUserDto()))

                .build();

    }


}


