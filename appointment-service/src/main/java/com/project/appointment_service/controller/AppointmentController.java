package com.project.appointment_service.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.appointment_service.dto.AppointmentDTO;
import com.project.appointment_service.dto.BulkAppointmentRequest;
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
	public Appointment addAppointment(@RequestBody AppointmentDTO appointment) {
		return appointmentService.addAppointment(appointment);
	}
	
	@DeleteMapping("/appointments/{id}")
	public Appointment deleteAppointmentById(@PathVariable Long id) {
		return appointmentService.deleteAppointmentById(id);
	}
	
	@PostMapping("/appointments/search/{doctorId}/{patientId}/{startDate}")
	public List<Appointment> search(@PathVariable("doctorId") Long doctorId, 
			@PathVariable("patientId") Long patientId, @PathVariable("startDate") LocalDateTime startDate){
		return appointmentService.search(doctorId, patientId, startDate);
	}
	
	@PostMapping("/appointments/{id}")
	public Appointment completeAppointment(@PathVariable Long id){
		return appointmentService.completeAppointment(id);
	}
	
	@GetMapping("/availability")
	public List<LocalTime> getAvailableSlot(@RequestParam("doctorId") Long doctorId,
			@RequestParam("date") LocalDateTime date){
		return appointmentService.getAvailableSlots(doctorId, date);
	}
	
	@PostMapping("/appointments/bulk")
	public List<Appointment> bulkBookAppointments(@RequestBody BulkAppointmentRequest request) {
		return appointmentService.bookAppointmentsInBulk(request);
	}
	
	@PutMapping("/appointments/reschedule/{id}")
	public Appointment reschedule(@PathVariable Long id, @RequestParam LocalDateTime newDateTime){
		return appointmentService.rescheduleAppointment(id, newDateTime);
	}
}
