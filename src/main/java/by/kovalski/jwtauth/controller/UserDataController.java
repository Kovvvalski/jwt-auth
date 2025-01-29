package by.kovalski.jwtauth.controller;

import by.kovalski.jwtauth.dto.UserDataDto;
import by.kovalski.jwtauth.service.JwtService;
import by.kovalski.jwtauth.service.UserDataService;
import by.kovalski.jwtauth.util.HttpRequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data_management")
@RequiredArgsConstructor
public class UserDataController {
    private static final String
    private final UserDataService dataService;
    private final JwtService jwtService;

    @RequestMapping(path = "/user_data/{userDataId}", method = RequestMethod.GET)
    public UserDataDto getUserData(@PathVariable Long userDataId) {
        return dataService.getById(userDataId);
    }

    @RequestMapping(path = "/user_data/{userDataId}", method = RequestMethod.DELETE)
    public UserDataDto deleteUserData(@PathVariable Long userDataId) {
        return dataService.deleteById(userDataId);
    }

    @RequestMapping(path = "/user_data/create", method = RequestMethod.POST)
    public UserDataDto createUserData(@RequestBody UserDataDto userDataDto, HttpServletRequest request) {

        return dataService.create(userDataDto);
    }

    @RequestMapping(path = "/user_data/{userDataId}", method = RequestMethod.PUT)
    public UserDataDto updateUserData(@PathVariable Long userDataId, @RequestBody UserDataDto userDataDto) {
        return dataService.updateById(userDataId, userDataDto);
    }
}
