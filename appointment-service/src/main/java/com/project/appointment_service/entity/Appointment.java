package com.project.appointment_service.entity;

import java.time.LocalDateTime;

import com.project.appointment_service.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "doctorId")
	private Long doctorId;
	
	@Column(name = "patientId")
	private Long patientId;
	
	@Column(name = "startDate")
	private LocalDateTime startDate;
	
	@Column(name = "endDate")
	private LocalDateTime endDate;
	
	@Column(name = "status")
	private Status status;
	
	@Column(name = "notes")
	private String notes;

	public Appointment() {
		super();
	}

	public Appointment(Long id, Long doctorId, Long patientId, LocalDateTime startDate, LocalDateTime endDate,
			Status status, String notes) {
		super();
		this.id = id;
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.notes = notes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", doctorId=" + doctorId + ", patientId=" + patientId + 
				", start=" + startDate + ", end=" + endDate + ", status=" + status + ", notes=" + notes + "]";
	}
}
