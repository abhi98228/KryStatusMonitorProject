package com.kry.api.statusmonitorproject.repository;

import com.kry.api.statusmonitorproject.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {
}
