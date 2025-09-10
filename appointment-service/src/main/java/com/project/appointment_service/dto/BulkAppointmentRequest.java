package com.project.appointment_service.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BulkAppointmentRequest {
	private Long doctorId;
    private Long patientId;
    private List<LocalDateTime> bookingTimes;

    public BulkAppointmentRequest() {}

    public BulkAppointmentRequest(Long doctorId, Long patientId, List<LocalDateTime> bookingTimes) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.bookingTimes = bookingTimes;
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

	public List<LocalDateTime> getBookingTimes() {
		return bookingTimes;
	}

	public void setBookingTimes(List<LocalDateTime> bookingTimes) {
		this.bookingTimes = bookingTimes;
	}

	@Override
	public String toString() {
		return "BulkAppointmentRequest [doctorId=" + doctorId + ", patientId=" + patientId + ", bookingTimes="
				+ bookingTimes + "]";
	}
}
