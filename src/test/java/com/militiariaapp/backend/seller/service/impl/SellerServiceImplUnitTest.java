package com.militiariaapp.backend.seller.service.impl;

import com.militiariaapp.backend.MilitariaUnitTests;
import com.militiariaapp.backend.appuser.model.AppUser;
import com.militiariaapp.backend.appuser.service.AppUserRepository;
import com.militiariaapp.backend.seller.model.Seller;
import com.militiariaapp.backend.seller.model.view.SellerCreationView;
import com.militiariaapp.backend.seller.model.view.SellerSummaryView;
import com.militiariaapp.backend.seller.service.repository.SellerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SellerServiceImplUnitTest extends MilitariaUnitTests {

    @Mock
    private SellerRepository repository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private SellerServiceImpl service;

    @Test
    void saveSeller_ShouldSaveSellerWhenViewIsValid() {
        var view = new SellerCreationView();
        view.setCompanyName("Test Company");
        view.setPhoneNumber("1234567890");
        view.setUser(UUID.randomUUID());

        when(appUserRepository.findById(any(UUID.class))).thenReturn(Optional.of(mock(AppUser.class)));
        when(repository.save(any(Seller.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.saveSeller(view);

        var captor = ArgumentCaptor.forClass(Seller.class);
        verify(repository, times(1)).save(captor.capture());
        Seller saved = captor.getValue();
        assertEquals("Test Company", saved.getCompanyName());
        assertEquals("1234567890", saved.getPhoneNumber());
    }

    @Test
    void getSellerById_ShouldReturnSummaryViewWhenSellerExists() {
        var id = UUID.randomUUID();
        var seller = new Seller();
        seller.setId(id);
        seller.setCompanyName("Existing Company");
        seller.setPhoneNumber("9876543210");

        when(repository.findById(id)).thenReturn(Optional.of(seller));

        SellerSummaryView result = service.getSellerById(id);

        assertNotNull(result);
        assertEquals("Existing Company", result.getCompanyName());
        assertEquals("9876543210", result.getPhoneNumber());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void getSellerById_ShouldReturnNullWhenSellerNotFound() {
        var id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.getSellerById(id));
        assertTrue(ex.getMessage().contains("Seller not found"));

    }
}
