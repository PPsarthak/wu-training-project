package com.project.appointment_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.appointment_service.entity.Appointment;
import com.project.appointment_service.repository.AppointmentRepository;

@Service
public class AppointmentService {
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	public Appointment getAppointmentById(Long id) {
		return appointmentRepository.findById(id).orElse(null);
	}
	
	public Appointment addAppointment(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}
	
	public String deleteAppointmentById(Long id) {
		appointmentRepository.deleteById(id);
		return "Appointment deleted successfully";
	}
	
	public List<Appointment> search(Long doctorId, Long patientId, LocalDateTime startDate){
		return appointmentRepository.findByDoctorIdAndPatientIdAndStartDate(doctorId, patientId, startDate);
	}
}
