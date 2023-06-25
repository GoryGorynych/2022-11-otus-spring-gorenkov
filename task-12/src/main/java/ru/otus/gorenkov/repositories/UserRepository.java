package ru.otus.gorenkov.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.gorenkov.models.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> getByUsername(String username);
}
