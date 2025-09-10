package com.project.appointment_service.pojo;

import java.util.List;

public class Doctor {
	private Long id;
	private String fullName;
	private String specialty;
	private String email;
	private String phone;
	private String locations;
    private List<WorkingHours> workingHours;
    
    
	public Doctor() {
		super();
	}


	public Doctor(Long id, String fullName, String specialty, String email, String phone, String locations,
			List<WorkingHours> workingHours) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.specialty = specialty;
		this.email = email;
		this.phone = phone;
		this.locations = locations;
		this.workingHours = workingHours;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public String getLocations() {
		return locations;
	}


	public void setLocations(String locations) {
		this.locations = locations;
	}


	public List<WorkingHours> getWorkingHours() {
		return workingHours;
	}


	public void setWorkingHours(List<WorkingHours> workingHours) {
		this.workingHours = workingHours;
	}


	@Override
	public String toString() {
		return "Doctor [id=" + id + ", fullName=" + fullName + ", specialty=" + specialty + ", email=" + email
				+ ", phone=" + phone + ", locations=" + locations + ", workingHours=" + workingHours + "]";
	}

}
