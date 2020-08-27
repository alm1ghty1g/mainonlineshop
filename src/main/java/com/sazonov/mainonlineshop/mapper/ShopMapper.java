package com.sazonov.mainonlineshop.mapper;


import com.sazonov.mainonlineshop.dto.CartDto;
import com.sazonov.mainonlineshop.dto.CategoryDto;
import com.sazonov.mainonlineshop.dto.LineItemDto;
import com.sazonov.mainonlineshop.dto.OrderDto;
import com.sazonov.mainonlineshop.shopentity.CartEntity;
import com.sazonov.mainonlineshop.shopentity.CategoryEntity;
import com.sazonov.mainonlineshop.shopentity.LineItemEntity;
import com.sazonov.mainonlineshop.shopentity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShopMapper {


    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserMapper userMapper;


    public Function<LineItemEntity, LineItemDto> lineItemToDto = p -> getLineItemDto(p);

    public Function<LineItemDto, LineItemEntity> lineItemToEntity = p -> getLineItemEntity(p);


    public <A, R> List<R> collectionToList(Collection<A> collection, Function<A, R> mapper) {

        return collection.stream().map(p -> mapper.apply(p)).collect(Collectors.toList());

    }

    public <A, R> Set<R> collectionToSet(Collection<A> collection, Function<A, R> mapper) {

        return collection.stream().map(p -> mapper.apply(p)).collect(Collectors.toSet());

    }




    public CartEntity getCartEntity(CartDto cartDto) {

    return CartEntity.builder()
            .id(cartDto.getId())
            .lineItemEntitySet(collectionToSet(cartDto.getLineItemDtoSet(), this::getLineItemEntity))

            .build();

    }


    public CartDto getCartDto(CartEntity cartEntity) {

        return CartDto.builder()
                .id(cartEntity.getId())
                .lineItemDtoSet(collectionToList(cartEntity.getLineItemEntitySet(), this::getLineItemDto))

                .build();

    }

    public CategoryEntity getCategoryEntity(CategoryDto categoryDto) {

        return CategoryEntity.builder()
                .name(categoryDto.getName())

                .build();

    }

    public CategoryDto getCategoryDto(CategoryEntity categoryEntity) {

        return CategoryDto.builder()
                .name(categoryEntity.getName())

                .build();

    }

    public LineItemDto getLineItemDto(LineItemEntity lineItemEntity) {

        return LineItemDto.builder()
                .id(lineItemEntity.getId())
                .product(productMapper.getProductDto(lineItemEntity.getProduct()))
                .quantity(lineItemEntity.getQuantity())

                .build();

    }

    public LineItemEntity getLineItemEntity(LineItemDto lineItemDto) {

        return LineItemEntity.builder()
                .id(lineItemDto.getId())
                .product(productMapper.getProductEntity(lineItemDto.getProduct()))
                .quantity(lineItemDto.getQuantity())

                .build();
    }


    public OrderDto getOrderDto(OrderEntity orderEntity) {

        return OrderDto.builder()
                .id(orderEntity.getId())
                .created(orderEntity.getCreated())
                .orderPrice(orderEntity.getOrderPrice())
                .lineItemDtoSet(collectionToList(orderEntity.getLineItemEntitySet(), lineItemToDto))
                .userDto(userMapper.getUserDto(orderEntity.getUserEntity()))
                .build();
    }


    public OrderEntity getOrderEntity(OrderDto orderDto) {//FIXME

        return OrderEntity.builder()
                .id(orderDto.getId())
                .created(orderDto.getCreated())
                .lineItemEntitySet(collectionToSet(orderDto.getLineItemDtoSet(), lineItemToEntity))
                .orderPrice(orderDto.getOrderPrice())
                .userEntity(userMapper.getUserEntity(orderDto.getUserDto()))
                .build();

    }




}
