package by.kovalski.jwtauth.controller;

import by.kovalski.jwtauth.dto.UserDataDto;
import by.kovalski.jwtauth.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data_management")
@RequiredArgsConstructor
public class UserDataManagementController {
    private final UserDataService dataService;

    @RequestMapping(path = "/user_data/{userDataId}", method = RequestMethod.GET)
    public UserDataDto getUserData(@PathVariable Long userDataId) {
        return dataService.getById(userDataId);
    }

    @RequestMapping(path = "/user_data/{userDataId}", method = RequestMethod.DELETE)
    public UserDataDto deleteUserData(@PathVariable Long userDataId) {
        return dataService.deleteById(userDataId);
    }

    @RequestMapping(path = "/create_user_data", method = RequestMethod.POST)
    public UserDataDto createUserData(@RequestBody UserDataDto userDataDto, @RequestAttribute String userId) {
        return dataService.create(userDataDto, userId);
    }

    @RequestMapping(path = "/user_data/{userDataId}", method = RequestMethod.PATCH)
    public UserDataDto updateUserData(@PathVariable Long userDataId, @RequestBody UserDataDto userDataDto) {
        return dataService.updateById(userDataId, userDataDto);
    }
}
