package com.project.appointment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.appointment_service.pojo.Patient;


@FeignClient(name = "ms-springboot-patient-doctor-appointment", url = "http://localhost:9393")
public interface PatientServiceFeignClient {
	@GetMapping("/patient/{id}")
	public Patient getPatient(@PathVariable Long id);
}
