package com.militiariaapp.backend.seller.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class Seller {

    @Id
    private UUID id = UUID.randomUUID();
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
}
