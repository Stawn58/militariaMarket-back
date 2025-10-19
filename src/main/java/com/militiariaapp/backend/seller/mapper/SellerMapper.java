package com.militiariaapp.backend.seller.mapper;

import com.militiariaapp.backend.seller.model.Seller;
import com.militiariaapp.backend.seller.model.view.SellerSummaryView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SellerMapper {

    SellerSummaryView toSummaryView(Seller seller);

    @Mapping(target = "id", ignore = true)
    Seller toEntity(SellerSummaryView sellerSummaryView);
}
