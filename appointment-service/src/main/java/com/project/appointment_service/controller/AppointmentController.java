package com.project.appointment_service.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.appointment_service.entity.Appointment;
import com.project.appointment_service.service.AppointmentService;

@RestController
@RequestMapping("/aapi")
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;
	
	@GetMapping()
	public String hello() {
		return "Hello, Appointment Service";
	}
	
	@GetMapping("/appointments/{id}")
	public Appointment getAppointmentById(@PathVariable Long id) {
		return appointmentService.getAppointmentById(id);
	}
	
	@PostMapping("/appointments")
	public Appointment addAppointment(@RequestBody Appointment appointment) {
		return appointmentService.addAppointment(appointment);
	}
	
	@DeleteMapping("/appointments/{id}")
	public String deleteAppointmentById(@PathVariable Long id) {
		return appointmentService.deleteAppointmentById(id);
	}
	
	@PostMapping("/appointments/search/{doctorId}/{patientId}/{startDate}")
	public List<Appointment> search(@PathVariable("doctorId") Long doctorId, 
			@PathVariable("patientId") Long patientId, @PathVariable("startDate") LocalDateTime startDate){
		return appointmentService.search(doctorId, patientId, startDate);
	}
}
