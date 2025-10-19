package com.militiariaapp.backend.user.service.impl;

import com.militiariaapp.backend.user.MilitariaUnitTests;
import com.militiariaapp.backend.user.model.User;
import com.militiariaapp.backend.user.model.view.UserSummaryView;
import com.militiariaapp.backend.user.service.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplUnitTest extends MilitariaUnitTests {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void saveUser_happyPath_savesAndReturnsId() {
        var view = new UserSummaryView();
        view.setFirstName("John");
        view.setLastName("Doe");
        view.setEmail("john@example.com");

        var generatedId = UUID.randomUUID();

        when(repository.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(generatedId);
            return u;
        });

        UUID result = service.saveUser(view);

        assertNotNull(result);
        assertEquals(generatedId, result);

        var captor = ArgumentCaptor.forClass(User.class);
        verify(repository, times(1)).save(captor.capture());
        User saved = captor.getValue();
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

}