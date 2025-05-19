package ru.maslenikov.springsecurityeducation.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.maslenikov.springsecurityeducation.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findUserByName(String username);
}
