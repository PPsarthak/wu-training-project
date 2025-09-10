package com.project.appointment_service.pojo;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class WorkingHours {
	private Long workingId;
    private Doctor doctor;
    private DayOfWeek dayOfWeek;
	private LocalTime startTime;
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
