package com.advanceschedular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class AdvanceSchedularApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdvanceSchedularApplication.class, args);
    }
}
