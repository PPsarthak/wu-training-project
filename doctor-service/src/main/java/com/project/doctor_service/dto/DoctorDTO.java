package com.project.doctor_service.dto;

import java.util.List;

import com.project.doctor_service.entity.WorkingHours;

import jakarta.persistence.Column;

public class DoctorDTO {
	private String fullName;
	private String specialty;
	private String email;
	private String phone;
	private List<String> locations;
	private List<WorkingHoursDTO> workingHours;
	
	public DoctorDTO() {
		super();
	}

	public DoctorDTO(String fullName, String specialty, String email, String phone, List<String> locations,
			List<WorkingHoursDTO> workingHours) {
		super();
		this.fullName = fullName;
		this.specialty = specialty;
		this.email = email;
		this.phone = phone;
		this.locations = locations;
		this.workingHours = workingHours;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<String> getLocations() {
		return locations;
	}

	public void setLocations(List<String> locations) {
		this.locations = locations;
	}

	public List<WorkingHoursDTO> getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(List<WorkingHoursDTO> workingHours) {
		this.workingHours = workingHours;
	}

	@Override
	public String toString() {
		return "DoctorDTO [fullName=" + fullName + ", specialty=" + specialty + ", email=" + email + ", phone=" + phone
				+ ", locations=" + locations + ", workingHours=" + workingHours + "]";
	}
}
