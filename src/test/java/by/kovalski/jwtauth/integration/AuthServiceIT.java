package by.kovalski.jwtauth.integration;

import by.kovalski.jwtauth.dto.AuthRequestDto;
import by.kovalski.jwtauth.dto.AuthResponseDto;
import by.kovalski.jwtauth.entity.User;
import by.kovalski.jwtauth.repository.UserRepository;
import by.kovalski.jwtauth.service.AuthService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
@TestPropertySource(locations = "classpath:application-test.properties")
class AuthServiceIT {

    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("password");

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    static void setUp() {
        postgresContainer.start();
    }

    @AfterAll
    static void tearDown() {
        postgresContainer.stop();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
    }

    @Test
    void shouldSignUpUser() {
        AuthRequestDto request = new AuthRequestDto("newUser", "password123");

        AuthResponseDto response = authService.signUp(request);

        assertNotNull(response);
        assertNotNull(response.getToken());

        User savedUser = userRepository.findByLogin("newUser").orElse(null);
        assertNotNull(savedUser);
        assertEquals("newUser", savedUser.getLogin());
    }
}
