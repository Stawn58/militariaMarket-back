package com.militiariaapp.backend.seller.service;

import com.militiariaapp.backend.seller.model.view.SellerCreationView;
import com.militiariaapp.backend.seller.model.view.SellerSummaryView;

import java.util.UUID;

public interface SellerService {

    void saveSeller(SellerCreationView sellerCreationView);

    SellerSummaryView getSellerById(UUID id);
}
