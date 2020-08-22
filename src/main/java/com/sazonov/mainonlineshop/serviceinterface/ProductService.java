package com.sazonov.mainonlineshop.serviceinterface;


import com.sazonov.mainonlineshop.dto.ProductDto;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto);

    ProductDto readProductByName(String name);

    ProductDto readProductById(int id);

    ProductDto updateProduct(ProductDto productDto);

    void deleteProduct(ProductDto productDto);
}
