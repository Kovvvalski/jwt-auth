package by.kovalski.jwtauth.service;

import by.kovalski.jwtauth.dto.AuthRequestDto;
import by.kovalski.jwtauth.dto.AuthResponseDto;
import by.kovalski.jwtauth.dto.UserDto;
import by.kovalski.jwtauth.entity.Role;
import by.kovalski.jwtauth.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private AuthRequestDto authRequestDto;
    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        authRequestDto = new AuthRequestDto("testUser", "password");
        userDto = new UserDto(1L, "testUser", "encodedPassword", Role.ROLE_USER);
        user = new User(1L, "testUser", "encodedPassword", Role.ROLE_USER);
    }

    @Test
    void shouldSignUpUser() {
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userService.create(any(UserDto.class))).thenReturn(userDto);
        when(userService.mapToEntity(userDto)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("jwt-token");

        AuthResponseDto response = authService.signUp(authRequestDto);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
    }

    @Test
    void shouldSignInUser() {
        when(userService.getByUsername("testUser")).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("jwt-token");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenAnswer(invocation -> null);


        AuthResponseDto response = authService.signIn(authRequestDto);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
    }
}
