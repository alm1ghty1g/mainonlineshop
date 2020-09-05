package com.sazonov.mainonlineshop.mapper;

import com.sazonov.mainonlineshop.dto.CreditCardDto;
import com.sazonov.mainonlineshop.dto.UserDto;
import com.sazonov.mainonlineshop.dto.formDto.AddCardDtoRequest;
import com.sazonov.mainonlineshop.exception.CreditCardIsAlreadyExistException;
import com.sazonov.mainonlineshop.repository.CreditCardRepository;
import com.sazonov.mainonlineshop.repository.UserRepository;
import com.sazonov.mainonlineshop.userentity.CreditCardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CreditCardMapper {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;


    public Function<CreditCardDto, CreditCardEntity> cardToEntity = p -> getCreditCardEntity(p);
    public Function<CreditCardEntity, CreditCardDto> cardToDto = p -> getCreditCardDto(p);

    public <A, R> List<R> collectionToList(Collection<A> collection, Function<A, R> mapper) {
        return collection.stream().map(p -> mapper.apply(p)).collect(Collectors.toList());
    }


    public CreditCardDto getCreditCardDtoToAddCard(AddCardDtoRequest addCardDtoRequest) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDto userDto = userMapper.getUserDto(userRepository.findByEmail(auth.getName()));

        return CreditCardDto.builder()
                .cardNumber(addCardDtoRequest.getCardNumber())
                .expirationDate(addCardDtoRequest.getExpirationDate())
                .cardType(addCardDtoRequest.getCardType())
                .userDto(userDto)
                .build();

    }

    public CreditCardDto getCreditCardDto(CreditCardEntity creditCardEntity) {

        return CreditCardDto.builder()
                .id(creditCardEntity.getId())
                .cardNumber(creditCardEntity.getCardNumber())
                .expirationDate(creditCardEntity.getExpirationDate())
                .cardType(creditCardEntity.getCardType())
                .userDto(userMapper.getUserDto(creditCardEntity.getUserEntity()))

                .build();
    }

    public CreditCardEntity getCreditCardEntity(CreditCardDto creditCardDto) {

        CreditCardEntity creditCardEntity = creditCardRepository.findByCardNumber(creditCardDto.getCardNumber());

        if (creditCardEntity != null) {
            throw new CreditCardIsAlreadyExistException("Credit Card with this number is already exist");
        } else

            return CreditCardEntity.builder()
                    .id(creditCardDto.getId())
                    .cardNumber(creditCardDto.getCardNumber())
                    .expirationDate(creditCardDto.getExpirationDate())
                    .cardType(creditCardDto.getCardType())
                    .userEntity(userMapper.getUserEntity(creditCardDto.getUserDto()))

                    .build();

    }

}


