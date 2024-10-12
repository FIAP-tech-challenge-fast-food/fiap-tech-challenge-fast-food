package com.fiap.techchallenge.fastfood.adapter.driver.controllers.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StressTestController {

    @GetMapping("/stress-test")
    public ResponseEntity<Void> runStressTest() {
        // Aumenta o consumo de Memoria
        byte[] memoryHog = new byte[300 * 1024]; 
        
        for (int i = 0; i < memoryHog.length; i++) {
            memoryHog[i] = (byte) (Math.random() * 256);
        }

        // Aumenta o consumo de CPU
        for (int i = 0; i < 1000; i++) {
            Math.sin(Math.random());
            Math.cos(Math.random());
            Math.pow(Math.random(), Math.random());
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}