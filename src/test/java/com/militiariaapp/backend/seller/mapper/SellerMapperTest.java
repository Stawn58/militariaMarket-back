package com.militiariaapp.backend.seller.mapper;

import com.militiariaapp.backend.MilitariaUnitTests;
import com.militiariaapp.backend.seller.model.Seller;
import com.militiariaapp.backend.seller.model.view.SellerSummaryView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class SellerMapperTest extends MilitariaUnitTests {

    private SellerMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(SellerMapper.class);
    }

    @Test
    void toSummaryView_ShouldMapAllFieldsWhenEntityIsValid() {
        Seller seller = new Seller();
        seller.setCompanyName("Test Company");
        seller.setPhoneNumber("1234567890");

        SellerSummaryView view = mapper.toSummaryView(seller);

        assertNotNull(view);
        assertEquals("Test Company", view.getCompanyName());
        assertEquals("1234567890", view.getPhoneNumber());
    }

    @Test
    void toEntity_ShouldMapAllFieldsWhenViewIsValid() {
        SellerSummaryView view = new SellerSummaryView();
        view.setCompanyName("Another Company");
        view.setPhoneNumber("9876543210");

        Seller seller = mapper.toEntity(view);

        assertNotNull(seller);
        assertEquals("Another Company", seller.getCompanyName());
        assertEquals("9876543210", seller.getPhoneNumber());
        assertNotNull(seller.getId());
    }

    @Test
    void mapping_ShouldReturnNullWhenInputsAreNull() {
        assertNull(mapper.toSummaryView(null));
        assertNull(mapper.toEntity(null));
    }
}
