package com.militiariaapp.backend.seller.model.view;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SellerCreationView {
    private String companyName;
    private String phoneNumber;
    private UUID user;
}
