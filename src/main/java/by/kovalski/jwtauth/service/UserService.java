package by.kovalski.jwtauth.service;

import by.kovalski.jwtauth.dto.UserDto;
import by.kovalski.jwtauth.entity.User;
import by.kovalski.jwtauth.exception.ServiceException;
import by.kovalski.jwtauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto getById(Long id) {
        return mapToDto(getUserEntityById(id));
    }

    @Transactional
    public UserDto create(UserDto userDto) {
        if (userRepository.existsByLogin(userDto.getLogin())) {
            throw new ServiceException("User with username " + userDto.getLogin() + " already exists");
        }
        userDto.setId(null);
        User user = mapToEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return mapToDto(userRepository.save(user));
    }

    @Transactional
    public UserDto update(Long id, UserDto userDto) {
        User user = getUserEntityById(id);
        user.setPassword(userDto.getPassword() != null ? passwordEncoder.encode(userDto.getPassword())
                                                       : user.getPassword());
        user.setRole(userDto.getRole() != null ? userDto.getRole() : user.getRole());
        return mapToDto(userRepository.save(user));
    }

    @Transactional
    public UserDto removeById(Long id) {
        User userToRemove = getUserEntityById(id);
        UserDto userDto = mapToDto(userToRemove);
        userRepository.delete(userToRemove);
        return userDto;
    }

    @Transactional
    public UserDto updatePassword(Long id, String newPassword) {
        User user = getUserEntityById(id);
        user.setPassword(passwordEncoder.encode(newPassword));
        return mapToDto(userRepository.save(user));
    }

    public User getByUsername(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new ServiceException("User with login " + login + " does not exist"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    User getUserEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ServiceException("User not found"));
    }

    User mapToEntity(UserDto userDto) {
        return new User(userDto.getId(), userDto.getLogin(), userDto.getPassword(), userDto.getRole());
    }

    UserDto mapToDto(User user) {
        return new UserDto(user.getId(), user.getLogin(), user.getPassword(), user.getRole());
    }
}