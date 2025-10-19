package com.militiariaapp.backend.user.mapper;

import com.militiariaapp.backend.user.MilitariaUnitTests;
import com.militiariaapp.backend.user.model.User;
import com.militiariaapp.backend.user.model.view.UserSummaryView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest extends MilitariaUnitTests {

    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    void toSummaryView_mapsAllFields() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        UserSummaryView view = mapper.toSummaryView(user);

        assertNotNull(view);
        assertEquals("John", view.getFirstName());
        assertEquals("Doe", view.getLastName());
        assertEquals("john.doe@example.com", view.getEmail());
    }

    @Test
    void toEntity_mapsAllFields() {
        UserSummaryView view = new UserSummaryView();
        view.setFirstName("Jane");
        view.setLastName("Smith");
        view.setEmail("jane.smith@example.com");

        User user = mapper.toEntity(view);

        assertNotNull(user);
        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals("jane.smith@example.com", user.getEmail());
        assertNotNull(user.getId());
    }

    @Test
    void nullInputs_returnNull() {
        assertNull(mapper.toSummaryView(null));
        assertNull(mapper.toEntity(null));
    }

}