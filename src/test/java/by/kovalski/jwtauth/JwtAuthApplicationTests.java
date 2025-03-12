package by.kovalski.jwtauth;

import by.kovalski.jwtauth.repository.UserDataRepository;
import by.kovalski.jwtauth.repository.UserRepository;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class JwtAuthApplicationTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserDataRepository userDataRepository;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @Test
    void contextLoads() {
        Assertions.assertTrue(true);
    }

}
