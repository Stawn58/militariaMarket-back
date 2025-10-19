package com.militiariaapp.backend.seller.model;

import com.militiariaapp.backend.appuser.model.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class Seller {

    @Id
    private UUID id = UUID.randomUUID();
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;
}
