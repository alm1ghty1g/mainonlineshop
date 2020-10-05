package com.sazonov.mainonlineshop.mapper;


import com.sazonov.mainonlineshop.dto.CategoryDto;
import com.sazonov.mainonlineshop.dto.ProductDto;
import com.sazonov.mainonlineshop.dto.formdto.AddProductDtoRequest;
import com.sazonov.mainonlineshop.exception.ProductIsAlreadyExistException;
import com.sazonov.mainonlineshop.repository.CategoryRepository;
import com.sazonov.mainonlineshop.repository.ProductRepository;
import com.sazonov.mainonlineshop.shopentity.CategoryEntity;
import com.sazonov.mainonlineshop.shopentity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductMapper {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShopMapper shopMapper;



    public Function<ProductDto, ProductEntity> productToEntity = p -> getProductEntityToAddProduct(p);
    public Function<ProductEntity, ProductDto> productToDto = p -> getProductDto(p);

    public Function<CategoryEntity, CategoryDto> categoryToDto = p -> getCategoryDto(p);
    public Function<CategoryDto, CategoryEntity> categoryToEntity = p -> getCategoryEntity(p);



    public <A, R> Set<R> collectionToSet(Collection<A> collection, Function<A, R> mapper) {
        return collection.stream().map(p -> mapper.apply(p)).collect(Collectors.toSet());
    }

    public <A, R> List<R> collectionToList(Collection<A> collection, Function<A, R> mapper) {
        return collection.stream().map(p -> mapper.apply(p)).collect(Collectors.toList());
    }




    public ProductDto getProductDtoToAddProduct(AddProductDtoRequest addProductDtoRequest) {

        return ProductDto.builder()
                .name(addProductDtoRequest.getName())
                .price(addProductDtoRequest.getPrice())
                .category(addProductDtoRequest.getCategory())
                .build();
    }


    public ProductEntity getProductEntityForOrder(ProductDto productDto) {

        return ProductEntity.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .expirationDate(LocalDate.now().plusDays(100))
                .category(categoryRepository.findById(productDto.getCategory()).orElse(new CategoryEntity(productDto.getCategory())))

                .build();
    }



    public ProductEntity getProductEntityToAddProduct(ProductDto productDto) {

        ProductEntity productEntity = productRepository.findByName(productDto.getName());

        if (productEntity != null) {
            throw new ProductIsAlreadyExistException("Product with this name is already exist in DB");
        } else

            return ProductEntity.builder()
                    .id(productDto.getId())
                    .name(productDto.getName())
                    .price(productDto.getPrice())
                    .expirationDate(LocalDate.now().plusDays(100))
                    .category(categoryRepository.findById(productDto.getCategory()).orElse(new CategoryEntity(productDto.getCategory())))

                    .build();
    }


    public ProductDto getProductDto(ProductEntity productEntity) {

        return ProductDto.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .expirationDate(productEntity.getExpirationDate())
                .category(productEntity.getCategory().getName())

                .build();
    }



    public ProductEntity getProductEntityForUpdate(ProductDto productDto) {

        ProductEntity productEntity = productRepository.findById(productDto.getId());

        productEntity.setName(productDto.getName());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setExpirationDate(productDto.getExpirationDate());
        productEntity.setCategory(categoryRepository.findById(productDto.getCategory()).orElse(new CategoryEntity(productDto.getCategory())));

        return productEntity;

    }



    public CategoryEntity getCategoryEntity(CategoryDto categoryDto) {

        return CategoryEntity.builder()
                .name(categoryDto.getName())
                .productSet(collectionToSet(categoryDto.getProductDtoSet(), p -> getProductEntityToAddProduct(p)))

                .build();

    }

    public CategoryDto getCategoryDto(CategoryEntity categoryEntity){

        return CategoryDto.builder()
                .name(categoryEntity.getName())
                .productDtoSet(collectionToSet(categoryEntity.getProductSet(), productToDto))

                .build();

    }


}
