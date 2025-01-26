package by.kovalski.jwtauth;

import jakarta.servlet.Filter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.servlet.configuration.WebMvcSecurityConfiguration;
import org.springframework.security.web.session.SessionManagementFilter;

@SpringBootApplication
public class JwtAuthApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JwtAuthApplication.class, args);
        System.out.println(context.getBean("springSecurityFilterChain").getClass());
    }

}
