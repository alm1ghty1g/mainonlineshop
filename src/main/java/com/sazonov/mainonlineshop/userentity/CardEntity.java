package com.sazonov.mainonlineshop.userentity;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter


@Entity
@Table(name = "card")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String cardNumber;

    @Column
    private String expirationDate;

    @Column
    private String cardType;

    @ManyToOne
    private UserEntity userEntity;
}
