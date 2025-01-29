package by.kovalski.jwtauth.service;

import by.kovalski.jwtauth.dto.UserDto;
import by.kovalski.jwtauth.entity.Role;
import by.kovalski.jwtauth.entity.User;
import by.kovalski.jwtauth.exception.ServiceException;
import by.kovalski.jwtauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ServiceException("User with id " + id + " not found"));
    }

    public UserDto create(UserDto userDto) {
        if (userRepository.existsByLogin(userDto.getLogin())) {
            throw new ServiceException("User with username " + userDto.getLogin() + " already exists");
        }
        userDto.setId(null);
        User user = mapToEntity(userDto);
        return mapToDto(userRepository.save(user));
    }

    public User getByUsername(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new ServiceException("User with login " + login + " does not exist"));

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    User mapToEntity(UserDto userDto) {
        return new User(userDto.getId(), userDto.getLogin(), userDto.getPassword(), userDto.getRole());
    }

    UserDto mapToDto(User user) {
        return new UserDto(user.getId(), user.getLogin(), user.getPassword(), user.getRole());
    }
}