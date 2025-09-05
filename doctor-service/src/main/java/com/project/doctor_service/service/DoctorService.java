package com.project.doctor_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.doctor_service.dto.DoctorDTO;
import com.project.doctor_service.entity.Doctor;
import com.project.doctor_service.entity.WorkingHours;
import com.project.doctor_service.repository.DoctorRepository;
import com.project.doctor_service.repository.WorkingHoursRepository;

@Service
public class DoctorService {
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private WorkingHoursRepository workingHoursRepository;
	
	public Doctor getDoctorById(Long id) {
		return doctorRepository.findById(id).orElse(null);
	}

	public Doctor addDoctor(DoctorDTO dto) {
        System.out.println("Received request: " + dto);

        Doctor d = new Doctor();
	    d.setFullName(dto.getFullName());
	    d.setEmail(dto.getEmail());
	    d.setPhone(dto.getPhone());
	    d.setSpecialty(dto.getSpecialty());
	    d.setLocations(String.join(", ", dto.getLocations()));

	    if (dto.getWorkingHours() != null && !dto.getWorkingHours().isEmpty()) {
	        List<WorkingHours> list = dto.getWorkingHours().stream()
	            .map(x -> {
	                WorkingHours wh = new WorkingHours();
	                wh.setDayOfWeek(x.getDayOfWeek());
	                wh.setStartTime(x.getStartTime());
	                wh.setEndTime(x.getEndTime());
	                wh.setDoctor(d);               
	                return wh;
	            }).toList();
	        d.setWorkingHours(list);               
	    }
	    return doctorRepository.save(d);
    }
	
	public Doctor updateDoctor(Long id, DoctorDTO dto) {
		Doctor existing = doctorRepository.findById(id).orElse(null);
		if(null == existing) {
			System.out.println("Null doctor");
			return null;
		}
		
		existing.setFullName(dto.getFullName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setSpecialty(dto.getSpecialty());
        existing.setLocations(String.join(", ", dto.getLocations()));
        existing.getWorkingHours().clear();
        
        if (dto.getWorkingHours() != null) {
            dto.getWorkingHours().forEach(whDto -> {
                WorkingHours wh = new WorkingHours();
                wh.setDayOfWeek(whDto.getDayOfWeek());
                wh.setStartTime(whDto.getStartTime());
                wh.setEndTime(whDto.getEndTime());
                wh.setDoctor(existing);
                existing.getWorkingHours().add(wh);
        });
        }
            
        return doctorRepository.save(existing);
    }

    public Doctor patchDoctor(Long id, DoctorDTO dto) {
    	Doctor existing = doctorRepository.findById(id).orElse(null);
    	if(null == existing) {
			System.out.println("Null doctor");
			return null;
		}
    	
    	if (dto.getFullName() != null) existing.setFullName(dto.getFullName());
        if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
        if (dto.getPhone() != null) existing.setPhone(dto.getPhone());
        if (dto.getSpecialty() != null) existing.setSpecialty(dto.getSpecialty());
        if (dto.getLocations() != null && !dto.getLocations().isEmpty()) {
            existing.setLocations(String.join(", ", dto.getLocations()));
        }

        if (dto.getWorkingHours() != null && !dto.getWorkingHours().isEmpty()) {
        	existing.getWorkingHours().clear();
            
            if (dto.getWorkingHours() != null) {
                dto.getWorkingHours().forEach(whDto -> {
                    WorkingHours wh = new WorkingHours();
                    wh.setDayOfWeek(whDto.getDayOfWeek());
                    wh.setStartTime(whDto.getStartTime());
                    wh.setEndTime(whDto.getEndTime());
                    wh.setDoctor(existing);
                    existing.getWorkingHours().add(wh);
            });
        }
        }
    	
        return doctorRepository.save(existing);
    }
       
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        doctorRepository.delete(doctor);
    }
	
}
