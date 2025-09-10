package com.project.appointment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.appointment_service.pojo.Doctor;

@FeignClient(name = "doctor-service", url = "http://localhost:9091/dapi")
public interface DoctorServiceFeignClient {
	@GetMapping("/doctors/{id}")
	public Doctor getDoctor(@PathVariable Long id);
}
