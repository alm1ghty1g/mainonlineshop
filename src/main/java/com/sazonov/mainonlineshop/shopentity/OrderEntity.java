package com.sazonov.mainonlineshop.shopentity;

import com.sazonov.mainonlineshop.userentity.UserEntity;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


@Entity
@Table(name = "orders")

public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private double orderPrice;


    @OneToMany
    private Set<LineItemEntity> lineItemEntitySet;


    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserEntity userEntity;


    @Column
    private LocalDate created;


}
