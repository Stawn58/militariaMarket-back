package com.militiariaapp.backend.seller.service.impl;

import com.militiariaapp.backend.appuser.service.AppUserRepository;
import com.militiariaapp.backend.seller.mapper.SellerMapper;
import com.militiariaapp.backend.seller.model.view.SellerCreationView;
import com.militiariaapp.backend.seller.model.view.SellerSummaryView;
import com.militiariaapp.backend.seller.service.SellerService;
import com.militiariaapp.backend.seller.service.repository.SellerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository repository;
    private final AppUserRepository appUserRepository;

    @Override
    @Transactional
    public void saveSeller(SellerCreationView sellerCreationView) {
        var mapper = Mappers.getMapper(SellerMapper.class);
        var sellerEntity = mapper.toEntity(sellerCreationView);

        var appUser = appUserRepository.findById(sellerCreationView.getUser())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        sellerEntity.setAppUser(appUser);
        //when using keycloak, try to save the appUserId is going to be stored in the jwt token

        repository.save(sellerEntity);
    }

    @Override
    public SellerSummaryView getSellerById(UUID id) {
        var mapper = Mappers.getMapper(SellerMapper.class);
        var sellerEntity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found"));

        return mapper.toSummaryView(sellerEntity);
    }
}
