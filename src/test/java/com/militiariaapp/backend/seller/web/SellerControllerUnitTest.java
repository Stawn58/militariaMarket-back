package com.militiariaapp.backend.seller.web;

import com.militiariaapp.backend.appuser.MilitariaUnitTests;
import com.militiariaapp.backend.seller.model.view.SellerSummaryView;
import com.militiariaapp.backend.seller.service.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SellerControllerUnitTest extends MilitariaUnitTests {

    @Mock
    private SellerService sellerService;

    @InjectMocks
    private SellerController controller;

    @Test
    void getSellers_sellerExists_returnsOk() {
        UUID id = UUID.randomUUID();
        SellerSummaryView view = new SellerSummaryView();
        view.setCompanyName("Test Company");
        view.setPhoneNumber("1234567890");

        when(sellerService.getSellerById(id)).thenReturn(view);

        ResponseEntity<SellerSummaryView> response = controller.getSellers(id);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Test Company", response.getBody().getCompanyName());
        assertEquals("1234567890", response.getBody().getPhoneNumber());
        verify(sellerService, times(1)).getSellerById(id);
    }

    @Test
    void getSellers_sellerNotFound_returnsNullBody() {
        UUID id = UUID.randomUUID();

        when(sellerService.getSellerById(id)).thenReturn(null);

        ResponseEntity<SellerSummaryView> response = controller.getSellers(id);

        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(sellerService, times(1)).getSellerById(id);
    }

    @Test
    void saveSeller_validInput_returnsOk() {
        SellerSummaryView view = new SellerSummaryView();
        view.setCompanyName("New Company");
        view.setPhoneNumber("5555555555");

        doNothing().when(sellerService).saveSeller(any(SellerSummaryView.class));

        ResponseEntity<Void> response = controller.saveSeller(view);

        assertEquals(200, response.getStatusCodeValue());
        verify(sellerService, times(1)).saveSeller(view);
    }
}
