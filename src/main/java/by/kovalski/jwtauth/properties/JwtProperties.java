package by.kovalski.jwtauth.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Getter
public class JwtProperties {

    private JwtData jwtData;

    @AllArgsConstructor
    @Getter
    @Setter
    public static class JwtData {
        private String secret;
        private Long expiration;
    }
}
