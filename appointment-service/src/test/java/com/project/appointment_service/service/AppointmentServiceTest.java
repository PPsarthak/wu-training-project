package com.project.appointment_service.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.project.appointment_service.constant.APP_CONSTANTS;
import com.project.appointment_service.entity.Appointment;
import com.project.appointment_service.enums.Status;
import com.project.appointment_service.pojo.Doctor;
import com.project.appointment_service.repository.AppointmentRepository;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {
	
	@Mock
	private AppointmentRepository appointmentRepository;
	
	@InjectMocks
	private AppointmentService appointmentService;
	
	private Appointment appointment;
	
	private Doctor doctor;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		appointment = new Appointment(1L, 1L, 1L, LocalDateTime.now(), 
				LocalDateTime.now().plusMinutes(APP_CONSTANTS.APPOINTMENT_BOOKING_LENGTH), 
				Status.BOOKED, "Follow uo");
		doctor = new Doctor();
	}
	
	@Test
	void test_getAppointmentById_happyPath() {
		when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(appointment));
		
		Appointment testAppointment = appointmentService.getAppointmentById(1L);
		assertNotNull(testAppointment);
	}
	
	@Test
	void test_getAppointmentById_noContent() {
		assertThrows(ResponseStatusException.class, 
				() -> appointmentService.getAppointmentById(1L));
	}
}
