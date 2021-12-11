package com.kry.api.statusmonitorproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StatusMonitorProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatusMonitorProjectApplication.class, args);
    }

}
