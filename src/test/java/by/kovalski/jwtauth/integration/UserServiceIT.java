package by.kovalski.jwtauth.integration;

import by.kovalski.jwtauth.dto.UserDto;
import by.kovalski.jwtauth.entity.Role;
import by.kovalski.jwtauth.repository.UserRepository;
import by.kovalski.jwtauth.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
@TestPropertySource(locations = "classpath:application-test.properties")
class UserServiceIT {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldCreateUser() {
        UserDto userDto = new UserDto(null, "testUser", "password", Role.ROLE_USER);
        UserDto createdUser = userService.create(userDto);

        assertNotNull(createdUser);
        assertEquals("testUser", createdUser.getLogin());
    }

    @Test
    void shouldDeleteUser() {
        UserDto userDto = new UserDto(null, "testUser", "password", Role.ROLE_USER);
        UserDto createdUser = userService.create(userDto);

        assertDoesNotThrow(() -> userService.removeById(createdUser.getId()));
    }
}
