package com.sazonov.mainonlineshop.serviceimplementation;

import com.sazonov.mainonlineshop.dto.*;
import com.sazonov.mainonlineshop.mapper.ProductMapper;
import com.sazonov.mainonlineshop.mapper.ShopMapper;
import com.sazonov.mainonlineshop.mapper.UserMapper;
import com.sazonov.mainonlineshop.repository.*;
import com.sazonov.mainonlineshop.serviceinterface.ShopService;
import com.sazonov.mainonlineshop.shopentity.*;
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
    private CategoryRepository categoryRepository;

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

        OrderDto orderDto = OrderDto.builder()
                .lineItemDtoSet(userDto.getCartDto().getLineItemDtoSet())
                .userShortResponseDto(UserShortResponseDto.builder()
                        .id(userDto.getId())
                        .firstName(userDto.getFirstName())
                        .lastName(userDto.getLastName())
                        .email(userDto.getEmail())
                        .phoneNumber(userDto.getPhoneNumber())
                        .build())
                .created(LocalDate.now())
                .orderPrice(userDto.getCartDto().countPrice())
                .status(OrderStatus.NEW.name())
                .build();

        OrderEntity orderEntity = shopMapper.getOrderEntity(orderDto);
        orderRepository.save(orderEntity);

        CartEntity cartEntity = shopMapper.getCartEntity(userDto.getCartDto());
        cartEntity.getLineItemEntitySet().clear();
        cartRepository.save(cartEntity);

        return shopMapper.getOrderDto(orderEntity);


    }


    public CategoryDto saveCategory(CategoryDto categoryDto) {

        CategoryEntity categoryEntity = shopMapper.getCategoryEntityToSave(categoryDto);

        categoryRepository.save(categoryEntity);

        return shopMapper.getCategoryDto(categoryEntity);
    }



    public CategoryDto getCategory(String name) {

        CategoryEntity categoryEntity = categoryRepository.findById(name).orElse(null);

        return shopMapper.getCategoryDto(categoryEntity);

    }
}
