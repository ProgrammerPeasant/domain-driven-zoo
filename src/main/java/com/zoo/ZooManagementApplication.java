package com.zoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZooManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZooManagementApplication.class, args);
    }
}