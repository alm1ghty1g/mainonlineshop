package com.sazonov.mainonlineshop.shopentity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@RequiredArgsConstructor
@Entity
@Table(name = "product")


public class ProductEntity {

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100, unique = false)
    private String name;


    @Column(nullable = false)
    private double price;


    private LocalDate expirationDate;

    @ManyToOne
    private CategoryEntity category;

}
