package com.kry.api.statusmonitorproject.service;

import com.kry.api.statusmonitorproject.model.Status;
import com.kry.api.statusmonitorproject.model.User;
import com.kry.api.statusmonitorproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidationService {
    @Autowired
    private UserRepository userRepository;

    public void validateDuplicateUrlOrServiceName(User user, Status status) {
        if (!user.getStatuses().contains(status))
            user.getStatuses().add(status);
        else
            throw new IllegalArgumentException("There is a service with same name or url already present for the user.");
    }

    public User validateAndFindUser(Integer id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (!foundUser.isPresent()) {
            throw new IllegalArgumentException("User Not Found.");
        }
        return foundUser.get();
    }

    public Status validateAndFindUserAndStatus(Integer id, String name) {
        return validateAndFindUserAndStatus(validateAndFindUser(id), name);
    }

    public Status validateAndFindUserAndStatus(User user, String name) {
        Optional<Status> foundStatus = user.getStatuses().stream().filter(status -> status.getName().equals(name)).findFirst();
        if (!foundStatus.isPresent()) {
            throw new IllegalArgumentException("Service Not Found.");
        }
        return foundStatus.get();
    }

    public void validateStatus(Status status) {
        if (status.getName().isBlank())
            throw new IllegalArgumentException(("Status name should not be empty."));
        if (status.getUrl().isBlank())
            throw new IllegalArgumentException(("Status url should not be empty."));
    }
}
