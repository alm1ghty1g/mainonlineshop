package com.sazonov.mainonlineshop.api;


import com.sazonov.mainonlineshop.dto.CartDto;
import com.sazonov.mainonlineshop.serviceinterface.ProductService;
import com.sazonov.mainonlineshop.serviceinterface.ShopService;
import com.sazonov.mainonlineshop.serviceinterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {



    @Autowired
    private ShopService shopService;



    @PostMapping("/add")
    public ResponseEntity<CartDto> addToCart(@RequestParam("id") int id) {


        return ResponseEntity.ok(shopService.addProductToCart(id));


    }

}
