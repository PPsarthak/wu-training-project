package com.project.appointment_service.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.project.appointment_service.client.DoctorServiceFeignClient;
import com.project.appointment_service.client.PatientServiceFeignClient;
import com.project.appointment_service.constant.APP_CONSTANTS;
import com.project.appointment_service.dto.AppointmentDTO;
import com.project.appointment_service.dto.BulkAppointmentRequest;
import com.project.appointment_service.entity.Appointment;
import com.project.appointment_service.enums.Status;
import com.project.appointment_service.pojo.Doctor;
import com.project.appointment_service.pojo.WorkingHours;
import com.project.appointment_service.repository.AppointmentRepository;

import jakarta.transaction.Transactional;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private DoctorServiceFeignClient doctorServiceFeignClient;
	
	@Autowired
	private PatientServiceFeignClient patientServiceFeignClient;
	
	public Appointment getAppointmentById(Long id) {
		return appointmentRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Appointment does not exists"));
	}
	
	public Appointment addAppointment(AppointmentDTO appointmentDTO) {
		Doctor doctor = doctorServiceFeignClient.getDoctor(appointmentDTO.getDoctorId());
		System.out.println(doctor);
		
		if(null == doctor) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Doctor Id does not exists");
		
		List<Appointment> existingAppointments = 
				appointmentRepository.findByDoctorIdAndStartDateAndStatus(
						doctor.getId(), appointmentDTO.getStartDate(), Status.BOOKED);
		
		System.out.println(existingAppointments);
		
		if(existingAppointments != null && existingAppointments.isEmpty()) {
			Appointment appointment = new Appointment();
			appointment.setDoctorId(doctor.getId());
			appointment.setPatientId(appointmentDTO.getPatientId());
			appointment.setStartDate(appointmentDTO.getStartDate());
			appointment.setEndDate(
					appointmentDTO.getStartDate().plusMinutes(APP_CONSTANTS.APPOINTMENT_BOOKING_LENGTH));
			appointment.setNotes(appointmentDTO.getNotes());
			appointment.setStatus(Status.BOOKED);
			
			Appointment savedAppointment = appointmentRepository.save(appointment);
			//TODO- Kafka: Send this savedAppointment to doctor-service and patient-service
			
			return savedAppointment;
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Booking Slot is already filled");
	}
	
	public Appointment deleteAppointmentById(Long id) {
		Appointment appointment = appointmentRepository.findById(id).orElse(null);
		if(appointment  == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Appointment does not exists");
		
		if(!appointment.getStatus().equals(Status.BOOKED)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Appointment is not scheduled");
		
		appointment.setStatus(Status.CANCELLED);
		return appointmentRepository.save(appointment);
	}
	
	public List<Appointment> search(Long doctorId, Long patientId, LocalDateTime startDate){
		return appointmentRepository.findByDoctorIdAndPatientIdAndStartDate(doctorId, patientId, startDate);
	}
	
	public Appointment completeAppointment(Long id) {
		Appointment appointment = appointmentRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment does not exists"));
		
		if(appointment.getStatus().equals(Status.COMPLETED)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment already completed");
		
		appointment.setStatus(Status.COMPLETED);
		return appointmentRepository.save(appointment);
	}
	
	public List<LocalTime> getAvailableSlots(Long doctorId, LocalDateTime date) {
		Doctor doctor = doctorServiceFeignClient.getDoctor(doctorId);
		System.out.println(doctor);
		
		if(doctor == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Doctor does not exists");
		
		DayOfWeek dayOfWeek = date.getDayOfWeek();

		List<WorkingHours> workingHoursForDay = doctor.getWorkingHours().stream().filter(wh -> wh.getDayOfWeek() == dayOfWeek).collect(Collectors.toList());
		if (workingHoursForDay.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT ,"No working hours found for doctor on " + dayOfWeek);

		List<LocalTime> allSlots = new ArrayList<>();
		for (WorkingHours wh : workingHoursForDay) {
			allSlots.addAll(generateSlots(wh.getStartTime(), wh.getEndTime()));
		}

		Set<LocalDateTime> bookedAppointments = 
				appointmentRepository.findByDoctorIdAndStartDateAndStatus(doctorId, date, Status.BOOKED)
				.stream().map(Appointment::getStartDate).collect(Collectors.toSet());
		
		return allSlots.stream().filter(slot -> !bookedAppointments.contains(slot)).sorted().collect(Collectors.toList());
	}

	private List<LocalTime> generateSlots(LocalTime start, LocalTime end) {
		List<LocalTime> slots = new ArrayList<>();
		LocalTime time = start;
		while (time.isBefore(end)) {
			slots.add(time);
			time = time.plusMinutes(APP_CONSTANTS.APPOINTMENT_BOOKING_LENGTH);
		}
		return slots;
	}

	public List<Appointment> bookAppointmentsInBulk(BulkAppointmentRequest request) {
		List<Appointment> savedAppointments = new ArrayList<>();

        for (LocalDateTime bookingTime : request.getBookingTimes()) {
            List<Appointment> exists = appointmentRepository.findByDoctorIdAndStartDateAndStatus(
            		request.getDoctorId(),bookingTime,Status.BOOKED);
            
            System.out.println(exists);
            if(!exists.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            		"Slot already booked at " + bookingTime);

            Appointment appointment = new Appointment();
            appointment.setDoctorId(request.getDoctorId());
            appointment.setPatientId(request.getPatientId());
            appointment.setStartDate(bookingTime);
            appointment.setEndDate(bookingTime.plusMinutes(APP_CONSTANTS.APPOINTMENT_BOOKING_LENGTH));
            appointment.setStatus(Status.BOOKED);
            
            appointmentRepository.save(appointment);
            savedAppointments.add(appointment);
        }

        return savedAppointments;
	}
	
	@Transactional
	public Appointment rescheduleAppointment(Long appointmentId, LocalDateTime newDateTime) {
		Appointment existing = appointmentRepository.findById(appointmentId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Appointment not found with id: " + appointmentId));

		if (existing.getStatus() != Status.BOOKED) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Only BOOKED appointments can be rescheduled. Current status: " + existing.getStatus());
		}

		Doctor doctor = doctorServiceFeignClient.getDoctor(existing.getDoctorId());
		System.out.println(doctor);
		
		DayOfWeek dayOfWeek = newDateTime.getDayOfWeek();
		List<WorkingHours> workingHoursForDay = doctor.getWorkingHours().stream().filter(wh -> wh.getDayOfWeek() == dayOfWeek).collect(Collectors.toList());
		if (workingHoursForDay.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT ,"No working hours found for doctor on " + dayOfWeek);

		boolean exists = appointmentRepository.findByDoctorIdAndStartDateAndStatus(
				existing.getDoctorId(), newDateTime, Status.BOOKED).isEmpty();
		if(!exists) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Slot is already booked");

		existing.setStartDate(newDateTime);
		existing.setEndDate(newDateTime.plusMinutes(30));
		
		return appointmentRepository.save(existing);
	}
}
