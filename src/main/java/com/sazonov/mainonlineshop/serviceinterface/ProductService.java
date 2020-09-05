package com.sazonov.mainonlineshop.serviceinterface;


import com.sazonov.mainonlineshop.dto.ProductDto;


import java.util.*;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto);

    Set<ProductDto> findProductByName(String name);

    ProductDto readProductById(int id);

    ProductDto updateProduct(ProductDto productDto);

    void deleteProduct(ProductDto productDto);

    List<ProductDto> getAllProducts();
}
