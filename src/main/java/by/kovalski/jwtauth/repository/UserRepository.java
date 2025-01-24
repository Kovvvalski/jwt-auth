package by.kovalski.jwtauth.repository;

import by.kovalski.jwtauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    boolean existsByUsername(String username);
}
