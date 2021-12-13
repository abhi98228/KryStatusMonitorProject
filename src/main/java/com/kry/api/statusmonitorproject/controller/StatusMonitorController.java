package com.kry.api.statusmonitorproject.controller;

import com.kry.api.statusmonitorproject.model.Status;
import com.kry.api.statusmonitorproject.model.User;
import com.kry.api.statusmonitorproject.repository.UserRepository;
import com.kry.api.statusmonitorproject.service.StatusMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/addservice/{id}/")
    public ResponseEntity addService(@PathVariable Integer id, @RequestBody Status status) {
        if (status.getName().isBlank())
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Status name should not be empty.");
        if (status.getUrl().isBlank())
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Status url should not be empty.");
        statusMonitorService.updateStatusHealth(status);
        Optional<User> foundUser = userRepository.findById(id);
        if (!foundUser.isPresent()) {
            List<Status> statusList = new ArrayList<>();
            statusList.add(status);
            User user = new User(id, statusList);
            userRepository.save(user);
            return ResponseEntity.ok("User Added");
        }
        foundUser.get().getStatuses().add(status);
        userRepository.save(foundUser.get());
        return ResponseEntity.ok("User Modified");
    }

    @GetMapping("/healthreports/{id}")
    public ResponseEntity getHealthReports(@PathVariable Integer id) {
        return ResponseEntity.ok(validateAndFindUser(id).getStatuses());
    }

    @GetMapping("healthreport/{id}/{name}")
    public ResponseEntity getHealthReport(@PathVariable Integer id, @PathVariable String name) {
        return ResponseEntity.ok(validateAndFindUserAndStatus(id,name));
    }

    @DeleteMapping("deleteservice/{id}/{name}")
    public ResponseEntity deleteService(@PathVariable Integer id, @PathVariable String name)
    {
        User foundUser = validateAndFindUser(id);
        foundUser.getStatuses().remove(validateAndFindUserAndStatus(foundUser,name));
        userRepository.save(foundUser);
        return ResponseEntity.ok("Service Deleted.");
    }

    private User validateAndFindUser(Integer id)
    {
        Optional<User> foundUser = userRepository.findById(id);
        if (!foundUser.isPresent()) {
           throw new IllegalArgumentException("User Not Found.");
        }
        return foundUser.get();
    }

    private Status validateAndFindUserAndStatus(Integer id, String name)
    {
        Optional<Status> foundService = validateAndFindUser(id).getStatuses().stream().filter(status -> status.getName().equals(name)).findFirst();
        if (!foundService.isPresent()) {
            throw new IllegalArgumentException("Service Not Found.");
        }
        return foundService.get();
    }

    private Status validateAndFindUserAndStatus(User user, String name)
    {
        Optional<Status> foundService = user.getStatuses().stream().filter(status -> status.getName().equals(name)).findFirst();
        if (!foundService.isPresent()) {
            throw new IllegalArgumentException("Service Not Found.");
        }
        return foundService.get();
    }
}
