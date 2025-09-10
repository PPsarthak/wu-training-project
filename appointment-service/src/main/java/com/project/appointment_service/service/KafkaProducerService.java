package com.project.appointment_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.project.appointment_service.entity.Appointment;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String APPOINTMENT_TOPIC = "appointment-topic";

    public void confirmAppointment(String message) {
        kafkaTemplate.send(APPOINTMENT_TOPIC, message);
        System.out.println("Sent Appointment: " + message);
    }
}