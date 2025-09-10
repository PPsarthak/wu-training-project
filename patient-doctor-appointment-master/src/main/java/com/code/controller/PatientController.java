package com.code.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.code.entity.Patient;
import com.code.exception.ResourceNotFoundException;
import com.code.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/papi")
public class PatientController {

	@Autowired
	PatientService patientService;

	@GetMapping("/patients")
	public List<Patient> getPatientList() {
		return patientService.findAllPatients();
	}

	@GetMapping("/patient/{id}")
	public Patient getPatient(@PathVariable Long id) throws ResourceNotFoundException {
		return patientService.getPatientById(id);

	}

//	// GET /papi/patients?name=ashu&page=0&size=10
//	    @GetMapping(value = "/patients", params = "name")
//	    public ResponseEntity<Page<Patient>> getPatientsByName(
//	            @RequestParam String name,
//	            @RequestParam(defaultValue = "0") int page,
//	            @RequestParam(defaultValue = "10") int size) {
//
//	        Pageable pageable = PageRequest.of(page, size);
//	        Page<Patient> patients = patientService.findPatientsByName(name, pageable);
//	        return ResponseEntity.ok(patients);
//	    }

	@PostMapping("/patient")
	public Patient savePatient(@Valid @RequestBody Patient patient) {
		return patientService.createPatient(patient);

	}

	// PATCH /papi/patients/{id}
	@PatchMapping("/patient/{id}")
	public Patient patchPatient(@PathVariable Long id, @RequestBody Map<String, Object> updates)
			throws ResourceNotFoundException {
		return patientService.patchPatients(id, updates);
	}

	@PutMapping("/patient/{id}")
	public Patient updatePatient(@PathVariable Long id, @RequestBody Patient patient) throws ResourceNotFoundException {
		return patientService.updatePatient(id, patient);
	}

	@DeleteMapping("/patient/{id}")
	public String deletePatient(@PathVariable Long id) {
		return patientService.deletePatient(id);
	}

//@GetMapping("/papi/patients")
//    public List<Patient> getPatients(
//            @RequestParam String name,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return patientService.getPatientsByName(name, page, size);
//    }

//	@GetMapping("/search")
//	public Page<Patient> getPatients(
//	        @RequestParam String name,
//	        @RequestParam(defaultValue = "0") int page,
//	        @RequestParam(defaultValue = "10") int size) {
//	    return patientService.getPatientsByName(name, page, size);
//	}

	@GetMapping(value = "/search", params = { "name", "page", "size" })
	public ResponseEntity<List<Patient>> getPatientsByNameAndAge(@RequestParam String name, 
			@RequestParam int page, @RequestParam int size) {
		// interpreted as age
		List<Patient> patients = patientService.findPatientsByNameAndAge(name, page, size);
		return ResponseEntity.ok(patients);
	}

}
