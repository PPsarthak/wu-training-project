package com.code.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.code.entity.Patient;
import com.code.exception.ResourceNotFoundException;
import com.code.repository.PatientRepository;

@Service
public class PatientService {

	@Autowired
	PatientRepository patientRepository;

	public List<Patient> findAllPatients() {
		return patientRepository.findAll();
	}

	public Patient getPatientById(Long id) throws ResourceNotFoundException {
		return patientRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Patient not found for this id :: " + id));
	}

	public Patient createPatient(Patient patient) {
		return patientRepository.save(patient);
	}

	public Patient updatePatient(Long patientId, Patient newPatient) throws ResourceNotFoundException {
		Patient patient = patientRepository.findById(patientId)
				.orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id :: " + patientId));

		patient.setFullName(newPatient.getFullName());
		patient.setEmail(newPatient.getEmail());
		patient.setPhone(newPatient.getPhone());
		patient.setDob(newPatient.getDob());

		return patientRepository.save(patient);
	}

	public Patient patchPatients(Long id, Map<String, Object> updates) throws ResourceNotFoundException {
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id :: " + id));

		updates.forEach((key, value) -> {
			switch (key) {
			case "fullName":
				patient.setFullName((String) value);
				break;
			case "email":
				patient.setEmail((String) value);
				break;
			case "phone":
				patient.setPhone((String) value);
				break;
			case "dob":
				patient.setDob(LocalDate.parse((String) value, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				break;
			}
		});

		return patientRepository.save(patient);
	}

//	 public List<Patient> getPatientsByName(String name, int page, int size) {
//	        return patientRepo.findByFullName(name);
//	    }

//public Page<Patient> getPatientsByName(String name, int page, int size) {
//    Pageable pageable = PageRequest.of(page, size);
//    return patientRepo.findByFullNameContainingIgnoreCase(name, pageable);
//}

	public List<Patient> findPatientsByNameAndAge(String name, int age, int size) {
		List<Patient> allMatchingPatients = patientRepository.findByFullNameContainingIgnoreCase(name);

		return allMatchingPatients.stream().filter(patient -> {
			LocalDate dob = patient.getDob();
			if (dob == null)
				return false;
			int calculatedAge = LocalDate.now().getYear() - dob.getYear();
			return calculatedAge == age;
		}).limit(size).toList();
	}

	public String deletePatient(Long patientId) {
		patientRepository.deleteById(patientId);
		return "Deleted";
	}
}
