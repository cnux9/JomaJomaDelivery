package com.example.jomajomadelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JomaJomaDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(JomaJomaDeliveryApplication.class, args);
    }

}