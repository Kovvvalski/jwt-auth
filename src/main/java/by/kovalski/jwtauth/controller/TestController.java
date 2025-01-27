package by.kovalski.jwtauth.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(path = "/say_hello", method = RequestMethod.GET)
    public String sayHello(HttpServletRequest request) {
        System.out.println(request.getParameter("test_attribute"));
        return "Hello World!";
    }
}
