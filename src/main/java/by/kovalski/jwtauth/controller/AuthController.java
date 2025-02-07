package by.kovalski.jwtauth.controller;

import by.kovalski.jwtauth.dto.AuthRequestDto;
import by.kovalski.jwtauth.dto.AuthResponseDto;
import by.kovalski.jwtauth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authenticationService;

    @RequestMapping(path = "/sign_up", method = RequestMethod.POST)
    public AuthResponseDto signUp(@RequestBody AuthRequestDto request) {
        return authenticationService.signUp(request);
    }

    @RequestMapping(path = "/sign_in", method = RequestMethod.POST)
    public AuthResponseDto signIn(@RequestBody AuthRequestDto request) {
        return authenticationService.signIn(request);
    }

}
