package com.kry.api.statusmonitorproject.scheduler;

import com.kry.api.statusmonitorproject.repository.UserRepository;
import com.kry.api.statusmonitorproject.service.StatusMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StatusMonitorScheduler {
    @Autowired
    private StatusMonitorService statusMonitorService;
    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedRateString =  "${update.rate}")
    public void scheduleFixedRateTask() {
        userRepository.findAll().stream().forEach(user -> statusMonitorService.monitorServicesForUser(user));
    }
}
