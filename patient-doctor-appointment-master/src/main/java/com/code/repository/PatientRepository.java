package com.code.repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.code.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
//	 Page<Patient> findByFullName(String fullName, Pageable pageable);
//	 Page<Patient> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);
	List<Patient> findByFullNameContainingIgnoreCase(String fullName);
	 
//	 List<Patient> findByFullName(String fullName);

}
