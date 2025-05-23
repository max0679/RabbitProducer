package ru.maslenikov.springsecurityeducation.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.maslenikov.springsecurityeducation.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findUserByName(String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles")
    Page<User> getAllUsers(Pageable pageable);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u IN :users")
    List<User> findUsersByUsers(@Param("users") List<User> users);

}
