package com.militiariaapp.backend.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class User {

    @Id
    private UUID id = UUID.randomUUID();
    private String firstName;
    private String lastName;
    @Email
    private String email;

}
