package com.project.doctor_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @KafkaListener(topics = "appointment-topic", groupId = "doctor-service-group")
    public void consume(String message) {
    	System.out.println("Recieved message: " + message);
    }
}
