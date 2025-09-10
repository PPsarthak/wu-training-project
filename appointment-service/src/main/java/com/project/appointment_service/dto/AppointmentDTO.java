package com.project.appointment_service.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;

public class AppointmentDTO {
	private Long doctorId;
	private Long patientId;
	private LocalDateTime startDate;
	private String notes;

	public AppointmentDTO() {
		super();
	}

	public AppointmentDTO(Long doctorId, Long patientId, LocalDateTime startDate, String notes) {
		super();
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.startDate = startDate;
		this.notes = notes;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "AppointmentDTO [doctorId=" + doctorId + ", patientId=" + patientId + ", startDate=" + startDate
				+ ", notes=" + notes + "]";
	}

}
