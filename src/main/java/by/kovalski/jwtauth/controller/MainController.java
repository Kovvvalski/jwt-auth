package by.kovalski.jwtauth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
public class MainController {

    @RequestMapping(method = RequestMethod.GET)
    public String sayHello(){
        return "Hello World!";
    }
}
