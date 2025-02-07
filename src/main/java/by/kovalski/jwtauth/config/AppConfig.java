package by.kovalski.jwtauth.config;

import by.kovalski.jwtauth.properties.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UrlPathHelper;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class AppConfig {

    @Bean
    public UrlPathHelper urlPathHelper() {
        return new UrlPathHelper();
    }
}
