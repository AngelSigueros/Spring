package com.sas.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users") // anotacion opcional
public class User {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String email;
    private String password;

    @OneToOne //(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

}
