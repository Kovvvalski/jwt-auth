package by.kovalski.jwtauth.controller;

import by.kovalski.jwtauth.dto.UserDto;
import by.kovalski.jwtauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user_management")
@RequiredArgsConstructor
public class UserManagementController {
    private final UserService userService;

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getById(userId);
    }

    @RequestMapping(path = "/create_user", method = RequestMethod.POST)
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.PATCH)
    public UserDto updateUser(@RequestBody UserDto userDto, @PathVariable("userId") Long userId) {
        return userService.update(userId, userDto);
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.DELETE)
    public UserDto deleteUser(@PathVariable Long userId) {
        return userService.removeById(userId);
    }
}
