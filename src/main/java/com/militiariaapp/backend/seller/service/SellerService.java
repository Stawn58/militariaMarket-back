package com.militiariaapp.backend.seller.service;

import com.militiariaapp.backend.seller.model.view.SellerSummaryView;

import java.util.UUID;

public interface SellerService {

    void saveSeller(SellerSummaryView sellerSummaryView);

    SellerSummaryView getSellerById(UUID id);
}
