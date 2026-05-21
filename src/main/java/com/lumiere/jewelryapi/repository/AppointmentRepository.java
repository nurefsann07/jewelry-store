package com.lumiere.jewelryapi.repository;

import com.lumiere.jewelryapi.entity.Appointment;
import com.lumiere.jewelryapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByAppointmentDateAndAppointmentTime(LocalDate date, String time);
    List<Appointment> findByAppointmentDate(LocalDate date);
    List<Appointment> findByUserOrderByAppointmentDateDescIdDesc(User user);
}