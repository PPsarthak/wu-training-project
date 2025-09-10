package com.code.springboot_hello.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.code.controller.PatientController;
import com.code.entity.Patient;
import com.code.service.PatientService;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Test
    void testGetPatientList() throws Exception {
        Patient p1 = new Patient();
        p1.setId(1L);
        p1.setFullName("Pawan");

        when(patientService.findAllPatients()).thenReturn(List.of(p1));

        mockMvc.perform(get("/papi/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Pawan"));
    }

    @Test
    void testGetPatientById() throws Exception {
        Patient p = new Patient();
        p.setId(1L);
        p.setFullName("Pawan");

        when(patientService.getPatientById(1L)).thenReturn(p);
//        thenReturn(Optional.of(p));

        mockMvc.perform(get("/papi/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Pawan"));
    }

//    @Test
    void testGetPatientById_NotFound() throws Exception {
        when(patientService.getPatientById(99L)).thenReturn(null);

        mockMvc.perform(get("/papi/patient/99"))
                .andExpect(status().is4xxClientError());
    }

//    @Test
    void testSavePatient() throws Exception {
        Patient p = new Patient();
        p.setId(1L);
        p.setFullName("New Patient");

        when(patientService.createPatient(any(Patient.class))).thenReturn(p);

        String json = """
                {
                  "id": 1,
                  "fullName": "New Patient",
                  "email": "test@example.com"
                }
                """;

        mockMvc.perform(post("/papi/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(1))
                .andExpect(jsonPath("$.message").value("Record is Saved Successfully!"));
    }

//    @Test
    void testDeletePatient() throws Exception {
        when(patientService.deletePatient(1L)).thenReturn("deleted");

        mockMvc.perform(delete("/papi/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted").value(true));
    }

    @Test
    void testSearchPatientsByNameAndAge() throws Exception {
        Patient p = new Patient();
        p.setId(1L);
        p.setFullName("Ashu");

        when(patientService.findPatientsByNameAndAge(eq("ashu"), eq(20), eq(30)))
                .thenReturn(List.of(p));

        mockMvc.perform(get("/papi/search")
                .param("name", "ashu")
                .param("page", "20")
                .param("size", "30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Ashu"));
    }

    @Test
    void testPatchPatient() throws Exception {
        Patient updatedPatient = new Patient();
        updatedPatient.setId(1L);
        updatedPatient.setFullName("Patched Name");

        when(patientService.patchPatients(eq(1L), any(Map.class))).thenReturn(updatedPatient);

        String json = """
                { "fullName": "Patched Name" }
                """;

        mockMvc.perform(patch("/papi/patient/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Patched Name"));
    }

    @Test
    void testPutPatient() throws Exception {
        Patient updatedPatient = new Patient();
        updatedPatient.setId(1L);
        updatedPatient.setFullName("Updated Full");

        when(patientService.updatePatient(eq(1L), any(Patient.class)))
                .thenReturn(updatedPatient);

        String json = """
                {
                  "id": 1,
                  "fullName": "Updated Full",
                  "email": "updated@example.com"
                }
                """;

        mockMvc.perform(put("/papi/patient/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Updated Full"));
    }
}

