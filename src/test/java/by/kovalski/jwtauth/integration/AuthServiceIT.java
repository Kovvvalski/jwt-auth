package by.kovalski.jwtauth.integration;

import by.kovalski.jwtauth.dto.AuthRequestDto;
import by.kovalski.jwtauth.dto.AuthResponseDto;
import by.kovalski.jwtauth.entity.User;
import by.kovalski.jwtauth.repository.UserRepository;
import by.kovalski.jwtauth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
@TestPropertySource(locations = "classpath:application-test.properties")
class AuthServiceIT {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

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
