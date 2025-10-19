package com.militiariaapp.backend.seller.service.impl;

import com.militiariaapp.backend.seller.mapper.SellerMapper;
import com.militiariaapp.backend.seller.model.view.SellerSummaryView;
import com.militiariaapp.backend.seller.service.SellerService;
import com.militiariaapp.backend.seller.service.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository repository;

    @Override
    public void saveSeller(SellerSummaryView sellerSummaryView) {
        var mapper = Mappers.getMapper(SellerMapper.class);
        var sellerEntity = mapper.toEntity(sellerSummaryView);

        repository.save(sellerEntity);
    }
}
