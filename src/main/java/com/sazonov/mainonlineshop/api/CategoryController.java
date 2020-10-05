package com.sazonov.mainonlineshop.api;


import com.sazonov.mainonlineshop.dto.CategoryDto;
import com.sazonov.mainonlineshop.dto.formdto.AddCategoryDtoRequest;
import com.sazonov.mainonlineshop.mapper.ShopMapper;
import com.sazonov.mainonlineshop.serviceinterface.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {


    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private ShopService shopService;

    @PostMapping("/add")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody AddCategoryDtoRequest addCategoryDtoRequest) {

        CategoryDto categoryDto = shopMapper.getCategoryDtoToAddCategory(addCategoryDtoRequest);

        return ResponseEntity.ok(shopService.saveCategory(categoryDto));

    }

    @GetMapping("/find/{name}")
    public ResponseEntity<CategoryDto> getCategoryInfo(@PathVariable("name") String name) {

       return ResponseEntity.ok(shopService.getCategory(name));


    }

}
