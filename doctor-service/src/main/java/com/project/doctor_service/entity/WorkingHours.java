package com.project.doctor_service.entity;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "wh")
public class WorkingHours {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long workingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId")
    @JsonBackReference
    private Doctor doctor;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "dayOfWeek")
    private DayOfWeek dayOfWeek;
    
	@Column(name = "startTime")
	private LocalTime startTime;

	@Column(name = "endTime")
	private LocalTime endTime;

	public WorkingHours() {
		super();
	}

	public WorkingHours(Long workingId, Doctor doctor, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
		super();
		this.workingId = workingId;
		this.doctor = doctor;
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Long getWorkingId() {
		return workingId;
	}

	public void setWorkingId(Long workingId) {
		this.workingId = workingId;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "WorkingHours [workingId=" + workingId + ", doctor=" + doctor + ", dayOfWeek=" + dayOfWeek
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
}

