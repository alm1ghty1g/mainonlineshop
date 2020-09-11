package com.sazonov.mainonlineshop.shopentity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Builder


@Entity
@Table(name = "category")



public class CategoryEntity {

    @Id
    @NonNull
    private String name;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private Set<ProductEntity> productSet = new HashSet<>();




}
