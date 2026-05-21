package com.lumiere.jewelryapi.dto;

import com.lumiere.jewelryapi.entity.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        String service,
        String duration,
        LocalDate appointmentDate,
        String appointmentTime,
        String advisor,
        LocalDateTime createdAt
) {
    public static AppointmentResponse from(Appointment a) {
        return new AppointmentResponse(
                a.getId(),
                a.getService(),
                a.getDuration(),
                a.getAppointmentDate(),
                a.getAppointmentTime(),
                a.getAdvisor(),
                a.getCreatedAt()
        );
    }
}