package com.sazonov.mainonlineshop.serviceinterface;

import com.sazonov.mainonlineshop.dto.*;

public interface ShopService {

    CartDto addProductToCart(int id);

    OrderDto placeOrder();

    CategoryDto saveCategory(CategoryDto categoryDto);

}
