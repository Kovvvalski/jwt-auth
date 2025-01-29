package by.kovalski.jwtauth.controller;

import by.kovalski.jwtauth.dto.UserDto;
import by.kovalski.jwtauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user_management")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @RequestMapping(path = "/create_user", method = RequestMethod.POST)
    public UserDto createUser(UserDto userDto) {
        return userService.create(userDto);
    }
}
