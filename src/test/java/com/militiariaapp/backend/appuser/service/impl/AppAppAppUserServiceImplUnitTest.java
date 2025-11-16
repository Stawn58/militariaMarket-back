package com.militiariaapp.backend.appuser.service.impl;

import com.militiariaapp.backend.MilitariaUnitTests;
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
    void saveUser_ShouldSaveAndReturnIdWhenViewIsValid() {
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
    void saveUser_ShouldThrowNullPointerExceptionWhenViewIsNull() {
        assertThrows(NullPointerException.class, () -> service.saveUser(null));
    }

    @Test
    void saveUser_ShouldSaveSuccessfullyWhenFieldsAreEmpty() {
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
    void saveUser_ShouldSaveSuccessfullyWhenFieldsHaveSpecialCharacters() {
        var view = new AppUserSummaryView();
        view.setFirstName("Jos\u00e9");
        view.setLastName("O'Brien");
        view.setEmail("jos\u00e9.o'brien@test.com");

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
        assertEquals("Jos\u00e9", saved.getFirstName());
        assertEquals("O'Brien", saved.getLastName());
        assertEquals("jos\u00e9.o'brien@test.com", saved.getEmail());
    }

}