package com.militiariaapp.backend.appuser.web;

import com.militiariaapp.backend.MilitariaUnitTests;
import com.militiariaapp.backend.appuser.model.view.AppUserCreationView;
import com.militiariaapp.backend.appuser.service.AppUserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AppUserControllerUnitTest extends MilitariaUnitTests {

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private AppUserController controller;

    @Test
    void createAppUser_withValidInput_returnsOkWithUUID() {
        UUID expectedId = UUID.randomUUID();
        AppUserCreationView request = new AppUserCreationView();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");

        when(appUserService.saveUserFromCreationView(request)).thenReturn(expectedId);

        ResponseEntity<UUID> response = controller.createAppUser(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedId, response.getBody());
    }

    @Test
    void createAppUser_withMinimalInput_returnsOkWithUUID() {
        UUID expectedId = UUID.randomUUID();
        AppUserCreationView request = new AppUserCreationView();
        request.setFirstName("Jane");
        request.setLastName("Smith");
        request.setEmail("jane@test.com");

        when(appUserService.saveUserFromCreationView(request)).thenReturn(expectedId);

        ResponseEntity<UUID> response = controller.createAppUser(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedId, response.getBody());
    }

    @Test
    void createAppUser_whenServiceThrowsException_exceptionPropagates() {
        AppUserCreationView request = new AppUserCreationView();
        request.setFirstName("Test");
        request.setLastName("User");
        request.setEmail("test@example.com");

        when(appUserService.saveUserFromCreationView(request))
                .thenThrow(new RuntimeException("Database connection failed"));

        assertThrows(RuntimeException.class, () -> controller.createAppUser(request));
    }

    @Test
    void createAppUser_returnsResponseEntityWithCorrectStatus() {
        UUID id = UUID.randomUUID();
        AppUserCreationView request = new AppUserCreationView();
        request.setFirstName("Bob");
        request.setLastName("Johnson");
        request.setEmail("bob@example.com");

        when(appUserService.saveUserFromCreationView(request)).thenReturn(id);

        ResponseEntity<UUID> response = controller.createAppUser(request);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }
}
