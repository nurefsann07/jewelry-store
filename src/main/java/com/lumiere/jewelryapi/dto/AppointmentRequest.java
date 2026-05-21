package com.lumiere.jewelryapi.dto;

import java.time.LocalDate;

public record AppointmentRequest(
        String service,
        String duration,
        LocalDate appointmentDate,
        String appointmentTime,
        String advisor
) {}