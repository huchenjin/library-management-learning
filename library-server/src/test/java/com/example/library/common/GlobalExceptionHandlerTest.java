package com.example.library.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldMapLearningTaskToHttp501() {
        var response = handler.handleBusinessException(new LearningTaskNotImplementedException("LEARNING-1"));
        assertEquals(501, response.getStatusCode().value());
        assertEquals(50100, response.getBody().code());
    }
}
