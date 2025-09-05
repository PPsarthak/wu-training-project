package com.project.doctor_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.doctor_service.dto.DoctorDTO;
import com.project.doctor_service.entity.Doctor;
import com.project.doctor_service.service.DoctorService;

@RestController
@RequestMapping("/dapi")
public class DoctorController {
	@Autowired
	private DoctorService doctorService;
	
	@GetMapping()
	public String hello() {
		return "Hello, Doctor Service";
	}
	
	@GetMapping("/doctors/{id}")
	public Doctor getDoctor(@PathVariable Long id) {
		return doctorService.getDoctorById(id);
	}
	
	@PostMapping("/doctors")
	public Doctor addDoctor(@RequestBody DoctorDTO doctorDTO) {
		return doctorService.addDoctor(doctorDTO);
	}
	
	@PutMapping("/doctors/{id}")
	public Doctor updateDoctor(@PathVariable Long id,@RequestBody DoctorDTO dto) {
		return doctorService.updateDoctor(id, dto);
	}
	
	@PatchMapping("/doctors/{id}")
	public Doctor patchDoctor(@PathVariable Long id,@RequestBody DoctorDTO dto) {
		return doctorService.patchDoctor(id, dto);
	}
	
	@DeleteMapping("/doctors/{id}")
	public String deleteDoctor(@PathVariable Long id) {
		doctorService.deleteDoctor(id);
		return "Deleted";
	}
}
