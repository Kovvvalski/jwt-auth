package by.kovalski.jwtauth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(path = "/say_hello", method = RequestMethod.GET)
    public String sayHello(HttpServletRequest request) {
        HttpSession session = request.getSession();
        StringBuilder out = new StringBuilder();
        for (var cookie : request.getCookies()) {
            out.append(cookie.getName()).append(": ").append(cookie.getValue()).append("\n");
        }
        return "Hello World!\n" + out;
    }
}
