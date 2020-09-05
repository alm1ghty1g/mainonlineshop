package com.sazonov.mainonlineshop.api;


import com.sazonov.mainonlineshop.dto.OrderDto;
import com.sazonov.mainonlineshop.serviceinterface.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ShopService shopService;


    @GetMapping("/make")
    public ResponseEntity<OrderDto> makeOrder() {

        return ResponseEntity.ok(shopService.placeOrder());

    }


}
