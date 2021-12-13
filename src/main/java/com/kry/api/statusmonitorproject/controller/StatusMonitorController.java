package com.kry.api.statusmonitorproject.controller;

import com.kry.api.statusmonitorproject.model.Status;
import com.kry.api.statusmonitorproject.model.User;
import com.kry.api.statusmonitorproject.repository.UserRepository;
import com.kry.api.statusmonitorproject.service.StatusMonitorService;
import com.kry.api.statusmonitorproject.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/statusMonitorService")
public class StatusMonitorController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatusMonitorService statusMonitorService;
    @Autowired
    private ValidationService validationService;

    @PostMapping("/addservice/{id}")
    public ResponseEntity addService(@PathVariable Integer id, @RequestBody Status status) {
        validationService.validateStatus(status);
        statusMonitorService.updateStatusHealth(status);
        Optional<User> foundUser = userRepository.findById(id);
        if (!foundUser.isPresent()) {
            List<Status> statusList = new ArrayList<>();
            statusList.add(status);
            User user = new User(id, statusList);
            userRepository.save(user);
            return ResponseEntity.ok("User Added");
        }
        validationService.validateDuplicateUrlOrServiceName(foundUser.get(), status);
        userRepository.save(foundUser.get());
        return ResponseEntity.ok("User Modified");
    }

    @GetMapping("/healthreports/{id}")
    public ResponseEntity getHealthReports(@PathVariable Integer id) {
        return ResponseEntity.ok(validationService.validateAndFindUser(id).getStatuses());
    }

    @GetMapping("healthreport/{id}/{name}")
    public ResponseEntity getHealthReport(@PathVariable Integer id, @PathVariable String name) {
        return ResponseEntity.ok(validationService.validateAndFindUserAndStatus(id, name));
    }

    @DeleteMapping("deleteservice/{id}/{name}")
    public ResponseEntity deleteService(@PathVariable Integer id, @PathVariable String name) {
        User foundUser = validationService.validateAndFindUser(id);
        foundUser.getStatuses().remove(validationService.validateAndFindUserAndStatus(foundUser, name));
        userRepository.save(foundUser);
        return ResponseEntity.ok("Service Deleted.");
    }
}
