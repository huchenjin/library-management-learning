package com.example.library.dto.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

class LoginRequestValidationTest {
    @Test
    void shouldRejectBlankCredentials() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            assertEquals(2, validator.validate(new LoginRequest("", "")).size());
        }
    }
}
