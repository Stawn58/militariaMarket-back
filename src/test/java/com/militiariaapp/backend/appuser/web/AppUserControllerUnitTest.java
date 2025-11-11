package com.militiariaapp.backend.appuser.web;

import com.militiariaapp.backend.appuser.MilitariaUnitTests;
import com.militiariaapp.backend.appuser.service.AppUserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class AppUserControllerUnitTest extends MilitariaUnitTests {

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private AppUserController controller;

    @Test
    void createAppUser_returnsOk() {
        ResponseEntity<Void> response = controller.createAppUser();

        assertEquals(200, response.getStatusCodeValue());
    }
}
