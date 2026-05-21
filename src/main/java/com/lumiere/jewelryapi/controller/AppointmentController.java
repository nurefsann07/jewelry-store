package com.lumiere.jewelryapi.controller;

import com.lumiere.jewelryapi.dto.AppointmentRequest;
import com.lumiere.jewelryapi.dto.AppointmentResponse;
import com.lumiere.jewelryapi.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<?> book(@RequestBody AppointmentRequest request, Authentication authentication) {
        try {
            AppointmentResponse response = appointmentService.book(authentication.getName(), request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public List<AppointmentResponse> myAppointments(Authentication authentication) {
        return appointmentService.getMyAppointments(authentication.getName());
    }

    @GetMapping("/booked-slots")
    public List<String> bookedSlots(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return appointmentService.getBookedSlots(date);
    }
}