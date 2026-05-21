package com.lumiere.jewelryapi.service;

import com.lumiere.jewelryapi.dto.AppointmentRequest;
import com.lumiere.jewelryapi.dto.AppointmentResponse;
import com.lumiere.jewelryapi.entity.Appointment;
import com.lumiere.jewelryapi.entity.User;
import com.lumiere.jewelryapi.repository.AppointmentRepository;
import com.lumiere.jewelryapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public AppointmentResponse book(String email, AppointmentRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        // Aynı tarih/saat dolu mu kontrol et
        if (appointmentRepository.existsByAppointmentDateAndAppointmentTime(
                request.appointmentDate(), request.appointmentTime())) {
            throw new RuntimeException("Bu tarih/saat dolu, lütfen başka bir saat seçin");
        }

        Appointment appointment = Appointment.builder()
                .user(user)
                .service(request.service())
                .duration(request.duration())
                .appointmentDate(request.appointmentDate())
                .appointmentTime(request.appointmentTime())
                .advisor(request.advisor())
                .build();

        return AppointmentResponse.from(appointmentRepository.save(appointment));
    }

    public List<String> getBookedSlots(LocalDate date) {
        return appointmentRepository.findByAppointmentDate(date).stream()
                .map(Appointment::getAppointmentTime)
                .toList();
    }

    public List<AppointmentResponse> getMyAppointments(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        return appointmentRepository.findByUserOrderByAppointmentDateDescIdDesc(user).stream()
                .map(AppointmentResponse::from)
                .toList();
    }
}