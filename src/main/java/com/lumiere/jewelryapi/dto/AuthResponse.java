package com.lumiere.jewelryapi.dto;

public record AuthResponse(
        String token,
        String message,
        Long userId,
        String email,
        String firstName,
        String lastName
) {}