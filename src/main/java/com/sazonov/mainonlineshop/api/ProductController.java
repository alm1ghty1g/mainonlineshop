package com.sazonov.mainonlineshop.api;


import com.sazonov.mainonlineshop.dto.ProductDto;
import com.sazonov.mainonlineshop.dto.formDto.AddProductDtoRequest;
import com.sazonov.mainonlineshop.mapper.ProductMapper;
import com.sazonov.mainonlineshop.serviceinterface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody AddProductDtoRequest addProductDtoRequest) {

        ProductDto productDto = productMapper.getProductDtoToAddProduct(addProductDtoRequest);

        return ResponseEntity.ok(productService.addProduct(productDto));

    }

    @GetMapping("/readProduct/{name}")
    public ResponseEntity<ProductDto> readProduct(@PathVariable("name") String name){

        return ResponseEntity.ok(productService.readProductByName(name));

    }


    @GetMapping("/updateProduct/{id}")
    public ResponseEntity<ProductDto> getProductForUpdate(@PathVariable("id") int id) {

        return ResponseEntity.ok(productService.readProductById(id));

    }

    @PostMapping("/updateProduct/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {

        return ResponseEntity.ok(productService.updateProduct(productDto));

    }

    @GetMapping("/deleteProduct/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {

        ProductDto productDto = productService.readProductById(id);

        productService.deleteProduct(productDto);

        return ResponseEntity.ok().build();
    }


}
