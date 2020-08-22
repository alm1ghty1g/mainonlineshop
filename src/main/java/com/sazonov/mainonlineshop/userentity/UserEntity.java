package com.sazonov.mainonlineshop.userentity;


import com.sazonov.mainonlineshop.shopentity.CartEntity;
import lombok.*;


import javax.persistence.*;
import java.time.LocalDate;



@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter



@Entity
@Table(name = "user")


public class UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(unique = true, nullable = false, length = 20)
    private String email;

    @Column
    private String password;

    @Column(nullable = false, length = 30)
    private String firstName;

    @Column(unique = true, nullable = false, length = 30)
    private String lastName;

    @Column(unique = true, nullable = false, length = 100)
    private String address;

    @Column(unique = true, nullable = false, length = 15)
    private String phoneNumber;

    private LocalDate created;

    private LocalDate updated;

    private LocalDate lastVisit;

    private String role;

    @OneToOne
    private CartEntity cartEntity;


}
