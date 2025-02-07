package by.kovalski.jwtauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class JwtAuthApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JwtAuthApplication.class, args);
    }

}
