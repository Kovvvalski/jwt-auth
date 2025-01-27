package by.kovalski.jwtauth.config;

import by.kovalski.jwtauth.properties.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class AppConfig {

}
