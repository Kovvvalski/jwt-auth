package by.kovalski.jwtauth.repository;

import by.kovalski.jwtauth.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Long> {

}
