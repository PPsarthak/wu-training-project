package com.project.appointment_service.pojo;

import java.time.LocalDate;

public class Patient {
	private Long id;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate dob;
	public Patient() {
		super();
	}
	public Patient(Long id, String fullName, String email, String phone, LocalDate dob) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.dob = dob;
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
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	@Override
	public String toString() {
		return "Patient [id=" + id + ", fullName=" + fullName + ", email=" + email + ", phone=" + phone + ", dob=" + dob
				+ "]";
	} 
    
    
}
