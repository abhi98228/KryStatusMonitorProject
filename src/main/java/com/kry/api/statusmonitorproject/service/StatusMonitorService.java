package com.kry.api.statusmonitorproject.service;

import com.kry.api.statusmonitorproject.model.Health;
import com.kry.api.statusmonitorproject.model.User;
import com.kry.api.statusmonitorproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
public class StatusMonitorService {
    @Autowired
    private UserRepository userRepository;

    public void monitorServicesForUser(User user) {
        RestTemplate restTemplate = new RestTemplate();
        user.getStatuses().stream().forEach(status -> {
            Health health = null;
            try {
                ResponseEntity<String> response
                        = restTemplate.getForEntity(status.getUrl() + "/actuator/health", String.class);
                if (response.getBody().contains("UP"))
                    health = new Health("UP", Instant.now());
            } catch (RestClientException e) {
                health = new Health("DOWN", Instant.now());
            }
            status.getHealthList().add(health);
        });
        userRepository.save(user);
    }
}
