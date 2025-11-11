package com.militiariaapp.backend.appuser.service.impl;

import com.militiariaapp.backend.appuser.MilitariaUnitTests;
import com.militiariaapp.backend.appuser.model.AppUser;
import com.militiariaapp.backend.appuser.model.view.AppUserSummaryView;
import com.militiariaapp.backend.appuser.service.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AppAppAppUserServiceImplUnitTest extends MilitariaUnitTests {

    @Mock
    private AppUserRepository repository;

    @InjectMocks
    private AppUserServiceImpl service;

    @Test
    void saveUser_happyPath_savesAndReturnsId() {
        var view = new AppUserSummaryView();
        view.setFirstName("John");
        view.setLastName("Doe");
        view.setEmail("john@example.com");

        var generatedId = UUID.randomUUID();

        when(repository.save(any(AppUser.class))).thenAnswer(invocation -> {
            AppUser u = invocation.getArgument(0);
            u.setId(generatedId);
            return u;
        });

        UUID result = service.saveUser(view);

        assertNotNull(result);
        assertEquals(generatedId, result);

        var captor = ArgumentCaptor.forClass(AppUser.class);
        verify(repository, times(1)).save(captor.capture());
        AppUser saved = captor.getValue();
        assertEquals("John", saved.getFirstName());
        assertEquals("Doe", saved.getLastName());
        assertEquals("john@example.com", saved.getEmail());
    }

    @Test
    void saveUser_null_throwsNullPointerException() {
        // mapstruct will return null for a null input; repository.save(null) mocked returns null
        // service then calls .getId() on the returned saved entity which will trigger NPE
        assertThrows(NullPointerException.class, () -> service.saveUser(null));
    }

    @Test
    void saveUser_withEmptyFields_savesSuccessfully() {
        var view = new AppUserSummaryView();
        view.setFirstName("");
        view.setLastName("");
        view.setEmail("");

        var generatedId = UUID.randomUUID();

        when(repository.save(any(AppUser.class))).thenAnswer(invocation -> {
            AppUser u = invocation.getArgument(0);
            u.setId(generatedId);
            return u;
        });

        UUID result = service.saveUser(view);

        assertNotNull(result);
        assertEquals(generatedId, result);

        var captor = ArgumentCaptor.forClass(AppUser.class);
        verify(repository, times(1)).save(captor.capture());
        AppUser saved = captor.getValue();
        assertEquals("", saved.getFirstName());
        assertEquals("", saved.getLastName());
        assertEquals("", saved.getEmail());
    }

    @Test
    void saveUser_withSpecialCharacters_savesSuccessfully() {
        var view = new AppUserSummaryView();
        view.setFirstName("José");
        view.setLastName("O'Brien");
        view.setEmail("josé.o'brien@test.com");

        var generatedId = UUID.randomUUID();

        when(repository.save(any(AppUser.class))).thenAnswer(invocation -> {
            AppUser u = invocation.getArgument(0);
            u.setId(generatedId);
            return u;
        });

        UUID result = service.saveUser(view);

        assertNotNull(result);
        assertEquals(generatedId, result);

        var captor = ArgumentCaptor.forClass(AppUser.class);
        verify(repository, times(1)).save(captor.capture());
        AppUser saved = captor.getValue();
        assertEquals("José", saved.getFirstName());
        assertEquals("O'Brien", saved.getLastName());
        assertEquals("josé.o'brien@test.com", saved.getEmail());
    }

}