package com.sazonov.mainonlineshop;

import com.sazonov.mainonlineshop.repository.CartRepository;
import com.sazonov.mainonlineshop.repository.CategoryRepository;
import com.sazonov.mainonlineshop.repository.ProductRepository;
import com.sazonov.mainonlineshop.repository.UserRepository;
import com.sazonov.mainonlineshop.shopentity.CartEntity;
import com.sazonov.mainonlineshop.shopentity.CategoryEntity;
import com.sazonov.mainonlineshop.shopentity.ProductEntity;
import com.sazonov.mainonlineshop.userentity.Roles;
import com.sazonov.mainonlineshop.userentity.UserEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class MainonlineshopApplication {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(MainonlineshopApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, CartRepository cartRepository, CategoryRepository categoryRepository,
                           ProductRepository productRepository) {

        return args -> {


            UserEntity admin = UserEntity.builder()
                    .email("admin@mail.com")
                    .password("$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu")
                    .firstName("admin")
                    .lastName("adminov")
                    .address("admin street")
                    .phoneNumber("111")
                    .active(true)
                    .created(LocalDate.now())
                    .updated(LocalDate.now())
                    .lastVisit(LocalDate.now())
                    .cartEntity(cartRepository.save(new CartEntity()))
                    .role(Roles.ROLE_ADMIN.name())
                    .build();

            userRepository.save(admin);



            UserEntity manager = UserEntity.builder()
                    .email("manager@mail.com")
                    .password("$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu")
                    .firstName("manager")
                    .lastName("managerov")
                    .address("manager street")
                    .phoneNumber("222")
                    .active(true)
                    .created(LocalDate.now())
                    .updated(LocalDate.now())
                    .lastVisit(LocalDate.now())
                    .cartEntity(cartRepository.save(new CartEntity()))
                    .role(Roles.ROLE_MANAGER.name())
                    .build();

            userRepository.save(manager);


            UserEntity customer = UserEntity.builder()
                    .email("customer@mail.com")
                    .password("$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu")
                    .firstName("customer")
                    .lastName("customerov")
                    .address("customer street")
                    .phoneNumber("333")
                    .active(true)
                    .created(LocalDate.now())
                    .updated(LocalDate.now())
                    .lastVisit(LocalDate.now())
                    .cartEntity(cartRepository.save(new CartEntity()))
                    .role(Roles.ROLE_CUSTOMER.name())
                    .build();

            userRepository.save(customer);

            UserEntity customer1 = UserEntity.builder()
                    .email("customer1@mail.com")
                    .password("$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu")
                    .firstName("customer1")
                    .lastName("customerov1")
                    .address("customerov1 street")
                    .phoneNumber("444")
                    .active(true)
                    .created(LocalDate.now())
                    .updated(LocalDate.now())
                    .lastVisit(LocalDate.now())
                    .cartEntity(cartRepository.save(new CartEntity()))
                    .role(Roles.ROLE_CUSTOMER.name())
                    .build();

            userRepository.save(customer1);




            CategoryEntity fruit = CategoryEntity.builder()
                    .name("fruit")
                    .build();

            CategoryEntity vegetable = CategoryEntity.builder()
                    .name("vegetable")
                    .build();

            CategoryEntity meat = CategoryEntity.builder()
                    .name("meat")
                    .build();

            categoryRepository.save(fruit);
            categoryRepository.save(vegetable);
            categoryRepository.save(meat);

            CategoryEntity appleCategory = CategoryEntity.builder()
                    .name("appleCategory")
                    .productSet(new HashSet<>())
                    .build();

            categoryRepository.save(appleCategory);
            Set<CategoryEntity> subCategories = Set.of(appleCategory);
            fruit.setSubCategories(subCategories);
            categoryRepository.save(fruit);

            ProductEntity apple = ProductEntity.builder()
                    .name("apple")
                    .price(1.11)
                    .expirationDate(LocalDate.now().plusDays(100))
                    .category(appleCategory)
                    .build();

            ProductEntity apple1 = ProductEntity.builder()
                    .name("apple1")
                    .price(1.11)
                    .expirationDate(LocalDate.now().plusDays(100))
                    .category(fruit)
                    .build();

            ProductEntity apple2 = ProductEntity.builder()
                    .name("apple2")
                    .price(1.11)
                    .expirationDate(LocalDate.now().plusDays(100))
                    .category(fruit)
                    .build();

            ProductEntity apple3 = ProductEntity.builder()
                    .name("apple3")
                    .price(1.11)
                    .expirationDate(LocalDate.now().plusDays(100))
                    .category(fruit)
                    .build();

            ProductEntity tomato = ProductEntity.builder()
                    .name("tomato")
                    .price(2.22)
                    .expirationDate(LocalDate.now().plusDays(100))
                    .category(vegetable)
                    .build();

            ProductEntity pork = ProductEntity.builder()
                    .name("pork")
                    .price(3.33)
                    .expirationDate(LocalDate.now().plusDays(100))
                    .category(meat)
                    .build();

            productRepository.save(apple);
            productRepository.save(apple1);
            productRepository.save(apple2);
            productRepository.save(apple3);
            productRepository.save(tomato);
            productRepository.save(pork);

            appleCategory.getProductSet().add(apple);
            categoryRepository.save(appleCategory);

        };

    }
}
