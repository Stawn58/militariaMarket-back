package com.militiariaapp.backend.appuser.mapper;

import com.militiariaapp.backend.appuser.MilitariaUnitTests;
import com.militiariaapp.backend.appuser.model.AppUser;
import com.militiariaapp.backend.appuser.model.view.AppUserSummaryView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class AppUserMapperTest extends MilitariaUnitTests {

    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    void toSummaryView_mapsAllFields() {
        AppUser appUser = new AppUser();
        appUser.setFirstName("John");
        appUser.setLastName("Doe");
        appUser.setEmail("john.doe@example.com");

        AppUserSummaryView view = mapper.toSummaryView(appUser);

        assertNotNull(view);
        assertEquals("John", view.getFirstName());
        assertEquals("Doe", view.getLastName());
        assertEquals("john.doe@example.com", view.getEmail());
    }

    @Test
    void toEntity_mapsAllFields() {
        AppUserSummaryView view = new AppUserSummaryView();
        view.setFirstName("Jane");
        view.setLastName("Smith");
        view.setEmail("jane.smith@example.com");

        AppUser appUser = mapper.toEntity(view);

        assertNotNull(appUser);
        assertEquals("Jane", appUser.getFirstName());
        assertEquals("Smith", appUser.getLastName());
        assertEquals("jane.smith@example.com", appUser.getEmail());
        assertNotNull(appUser.getId());
    }

    @Test
    void nullInputs_returnNull() {
        assertNull(mapper.toSummaryView(null));
        assertNull(mapper.toEntity(null));
    }

}