package com.project.appointment_service.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.appointment_service.entity.Appointment;
import com.project.appointment_service.enums.Status;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
	List<Appointment> findByDoctorIdAndStartDateAndStatus(Long doctorId, LocalDateTime startDate, Status status);
	List<Appointment> findByDoctorIdAndPatientIdAndStartDate(Long doctorId, Long patientId, LocalDateTime startDate);
}
