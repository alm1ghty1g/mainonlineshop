package com.sazonov.mainonlineshop.serviceimplementation;


import com.sazonov.mainonlineshop.dto.ProductDto;
import com.sazonov.mainonlineshop.exception.ProductIsNotExistException;
import com.sazonov.mainonlineshop.mapper.ProductMapper;
import com.sazonov.mainonlineshop.repository.CategoryRepository;
import com.sazonov.mainonlineshop.repository.ProductRepository;
import com.sazonov.mainonlineshop.serviceinterface.ProductService;
import com.sazonov.mainonlineshop.shopentity.CategoryEntity;
import com.sazonov.mainonlineshop.shopentity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryRepository categoryRepository;



    public ProductDto addProduct(ProductDto productDto) {

        ProductEntity productEntity = productMapper.getProductEntityToAddProduct(productDto);

        if (categoryRepository.findById(productEntity.getCategory().getName()).isEmpty()) {
            CategoryEntity ce = categoryRepository.save(productEntity.getCategory());
            productEntity.setCategory(ce);
        }
        productEntity = productRepository.save(productEntity);

        return productMapper.getProductDto(productEntity);
    }



    public Set<ProductDto> findProductByName(String name) {

        return productMapper.collectionToSet(productRepository.findAllByNameContains(name), productMapper.productToDto);
    }


    public ProductDto readProductById(int id) {

        ProductEntity productEntity = productRepository.findById(id);

        return productMapper.getProductDto(productEntity);

    }


    public ProductDto updateProduct(ProductDto productDto) {

        ProductEntity productEntity = productMapper.getProductEntityForUpdate(productDto);

        productRepository.save(productEntity);

        return productMapper.getProductDto(productEntity);

    }


    public void deleteProduct(ProductDto productDto) {

        ProductEntity productEntity = productRepository.findById(productDto.getId());
        productRepository.delete(productEntity);

    }


    public List<ProductDto> getAllProducts() {

        List<ProductEntity> productEntityList = productRepository.findAll();

        return productMapper.collectionToList(productEntityList, productMapper.productToDto);
    }
}
