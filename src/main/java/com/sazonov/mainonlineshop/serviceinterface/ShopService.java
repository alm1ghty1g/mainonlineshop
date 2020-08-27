package com.sazonov.mainonlineshop.serviceinterface;

import com.sazonov.mainonlineshop.dto.CartDto;
import com.sazonov.mainonlineshop.dto.OrderDto;
import com.sazonov.mainonlineshop.dto.ProductDto;
import com.sazonov.mainonlineshop.dto.UserDto;

public interface ShopService {

    CartDto addProductToCart(int id);

    OrderDto placeOrder();

}
