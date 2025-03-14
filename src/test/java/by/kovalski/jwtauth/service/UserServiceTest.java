package by.kovalski.jwtauth.service;

import by.kovalski.jwtauth.dto.UserDto;
import by.kovalski.jwtauth.entity.Role;
import by.kovalski.jwtauth.entity.User;
import by.kovalski.jwtauth.exception.ServiceException;
import by.kovalski.jwtauth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        user = new User(1L, "testUser", "encodedPassword", Role.ROLE_USER);
        userDto = new UserDto(1L, "testUser", "rawPassword", Role.ROLE_USER);
    }

    @Test
    void shouldGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDto result = userService.getById(1L);
        assertEquals(userDto.getLogin(), result.getLogin());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> userService.getById(2L));
    }

    @Test
    void shouldCreateUser() {
        when(userRepository.existsByLogin("testUser")).thenReturn(false);
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto createdUser = userService.create(userDto);

        assertNotNull(createdUser);
        assertEquals("testUser", createdUser.getLogin());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldNotCreateDuplicateUser() {
        when(userRepository.existsByLogin("testUser")).thenReturn(true);
        assertThrows(ServiceException.class, () -> userService.create(userDto));
    }

    @Test
    void shouldUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPassword")).thenReturn("newEncodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto updatedUser = userService.update(1L, new UserDto(null, null, "newPassword", null));

        assertEquals("newEncodedPassword", updatedUser.getPassword());
    }

    @Test
    void shouldDeleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        UserDto deletedUser = userService.removeById(1L);

        assertEquals(user.getLogin(), deletedUser.getLogin());
        verify(userRepository).delete(user);
    }
}
