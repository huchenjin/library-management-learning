package com.example.library.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ApiResponseTest {
    @Test
    void shouldCreateStableSuccessEnvelope() {
        ApiResponse<String> response = ApiResponse.success("ok");
        assertEquals(0, response.code());
        assertEquals("success", response.message());
        assertEquals("ok", response.data());
    }

    @Test
    void shouldCreateStableFailureEnvelope() {
        ApiResponse<Void> response = ApiResponse.failure(ErrorCode.NOT_FOUND, "图书不存在");
        assertEquals(40400, response.code());
        assertEquals("图书不存在", response.message());
        assertNull(response.data());
    }
}
