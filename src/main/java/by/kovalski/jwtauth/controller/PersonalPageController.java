package by.kovalski.jwtauth.controller;

import by.kovalski.jwtauth.dto.UserDto;
import by.kovalski.jwtauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personal_page")
@RequiredArgsConstructor
public class PersonalPageController {
    private final UserService userService;

    @RequestMapping(path = "/personal_data", method = RequestMethod.GET)
    public UserDto getPersonalInfo(@RequestAttribute Long userId) {
        return userService.getById(userId);
    }

    @RequestMapping(path = "/change_password", method = RequestMethod.PATCH)
    public UserDto changePassword(@RequestAttribute Long userId, @RequestBody UserDto userDto) {
        return userService.updatePassword(userId, userDto.getPassword());
    }
}
