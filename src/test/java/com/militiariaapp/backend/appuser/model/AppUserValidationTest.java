package com.militiariaapp.backend.appuser.model;

import com.militiariaapp.backend.appuser.MilitariaUnitTests;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AppUserValidationTest extends MilitariaUnitTests {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validEmail_passesValidation() {
        AppUser user = new AppUser();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        Set<ConstraintViolation<AppUser>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidEmail_failsValidation() {
        AppUser user = new AppUser();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("not-an-email");

        Set<ConstraintViolation<AppUser>> violations = validator.validate(user);

        assertEquals(1, violations.size());
        ConstraintViolation<AppUser> violation = violations.iterator().next();
        assertEquals("email", violation.getPropertyPath().toString());
    }

    @Test
    void emptyEmail_passesValidation() {
        // @Email annotation allows empty/null values by default
        AppUser user = new AppUser();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("");

        Set<ConstraintViolation<AppUser>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }

    @Test
    void nullEmail_passesValidation() {
        // @Email annotation allows empty/null values by default
        AppUser user = new AppUser();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail(null);

        Set<ConstraintViolation<AppUser>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }
}
