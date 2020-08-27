package com.sazonov.mainonlineshop.serviceimplementation;

import com.sazonov.mainonlineshop.dto.CartDto;
import com.sazonov.mainonlineshop.dto.OrderDto;
import com.sazonov.mainonlineshop.dto.ProductDto;
import com.sazonov.mainonlineshop.dto.UserDto;
import com.sazonov.mainonlineshop.mapper.ProductMapper;
import com.sazonov.mainonlineshop.mapper.ShopMapper;
import com.sazonov.mainonlineshop.mapper.UserMapper;
import com.sazonov.mainonlineshop.repository.*;
import com.sazonov.mainonlineshop.serviceinterface.ShopService;
import com.sazonov.mainonlineshop.shopentity.CartEntity;
import com.sazonov.mainonlineshop.shopentity.LineItemEntity;
import com.sazonov.mainonlineshop.shopentity.OrderEntity;
import com.sazonov.mainonlineshop.shopentity.ProductEntity;
import com.sazonov.mainonlineshop.userentity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private LineItemRepository lineItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;



    public CartDto addProductToCart(int id) {

        ProductEntity productEntity = productRepository.findById(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserEntity userEntity = userRepository.findByEmail(auth.getName());

        System.out.println("-----> " + auth.getName());

        CartEntity cartEntity = userEntity.getCartEntity();



        List<LineItemEntity> items = cartEntity.getLineItemEntitySet().stream()
                .filter(item -> item.getProduct().getId() == productEntity.getId())
                .collect(Collectors.toList());

        if (items.isEmpty()) {

            LineItemEntity lineItemEntity = LineItemEntity.builder()
                    .quantity(1)
                    .product(productEntity)
                    .build();

            lineItemRepository.save(lineItemEntity);

            cartEntity.getLineItemEntitySet().add(lineItemEntity);

        } else if (items.size() == 1) {

            items.get(0).increaseAmount();

        }

        cartRepository.save(cartEntity);

        return shopMapper.getCartDto(cartEntity);

    }


    public OrderDto placeOrder() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserEntity userEntity = userRepository.findByEmail(auth.getName());

        UserDto userDto = userMapper.getUserDto(userEntity);

        OrderDto orderDto = new OrderDto().builder()
                .lineItemDtoSet(userDto.getCartDto().getLineItemDtoSet())
                .userDto(userDto)
                .created(LocalDate.now())

                .build();

        OrderEntity orderEntity = shopMapper.getOrderEntity(orderDto);
        orderRepository.save(orderEntity);

        CartEntity cartEntity = shopMapper.getCartEntity(userDto.getCartDto());
        cartEntity.getLineItemEntitySet().clear();
        cartRepository.save(cartEntity);

        return shopMapper.getOrderDto(orderEntity);


    }
}
