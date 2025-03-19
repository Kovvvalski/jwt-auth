package by.kovalski.jwtauth.integration;

import by.kovalski.jwtauth.dto.UserDto;
import by.kovalski.jwtauth.entity.Role;
import by.kovalski.jwtauth.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
@TestPropertySource(locations = "classpath:application-test.properties")
class UserServiceIT {

    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("password");

    @Autowired
    private UserService userService;

    @DynamicPropertySource
    static void setProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
    }

    @BeforeAll
    static void setUp() {
        postgresContainer.start();
    }

    @AfterAll
    static void tearDown() {
        postgresContainer.stop();
    }

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
