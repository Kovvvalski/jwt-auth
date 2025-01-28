package by.kovalski.jwtauth.controller;

import by.kovalski.jwtauth.dto.UserDataDto;
import by.kovalski.jwtauth.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data_management")
@RequiredArgsConstructor
public class UserDataController {
    private final UserDataService dataService;

    @RequestMapping(path = "/user_data/{userDataId}", method = RequestMethod.GET)
    public UserDataDto getUserData(@PathVariable Long userDataId){
        return dataService.getById(userDataId);
    }
}
