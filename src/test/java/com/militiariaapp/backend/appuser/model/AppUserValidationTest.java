package com.militiariaapp.backend.appuser.model;

import com.militiariaapp.backend.MilitariaUnitTests;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppUserValidationTest extends MilitariaUnitTests {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateEmail_ShouldPassWhenEmailIsValid() {
        AppUser user = new AppUser();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        Set<ConstraintViolation<AppUser>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }

    @Test
    void validateEmail_ShouldFailWhenEmailIsInvalid() {
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
    void validateEmail_ShouldPassWhenEmailIsEmpty() {
        AppUser user = new AppUser();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("");

        Set<ConstraintViolation<AppUser>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }

    @Test
    void validateEmail_ShouldPassWhenEmailIsNull() {
        AppUser user = new AppUser();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail(null);

        Set<ConstraintViolation<AppUser>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }
}
