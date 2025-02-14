package by.kovalski.jwtauth.config;

import by.kovalski.jwtauth.filter.Oauth2TokenUserIdResolverFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private static final String ADMIN_ROLE_NAME = "ADMIN";
    private static final String USER_ROLE_NAME = "USER";
    private static final String USER_NAME_CLAIM_NAME = "preferred_username";
    private static final String ROLES_LIST = "spring_security_roles";
    private static final String ROLE_PREFIX = "ROLE_";


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, Oauth2TokenUserIdResolverFilter filter,
                                                   UserDataManagementAuthorizationManager userDataManagementAuthorizationManager) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOriginPatterns(List.of("*"));
            corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            corsConfiguration.setAllowedHeaders(List.of("*"));
            corsConfiguration.setAllowCredentials(true);
            return corsConfiguration;
        }));

        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        http.authorizeHttpRequests(request -> request
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/user_management/**").hasRole(ADMIN_ROLE_NAME)
                .requestMatchers("/data_management/create_user_data").hasRole(USER_ROLE_NAME)
                .requestMatchers("/data_management/user_data/*").access(userDataManagementAuthorizationManager)
                .anyRequest().authenticated());

        http.sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS));
        http.addFilterAfter(filter, BearerTokenAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var converter = new JwtAuthenticationConverter();
        var jwtAuthenticationConverter = new JwtGrantedAuthoritiesConverter();
        converter.setPrincipalClaimName(USER_NAME_CLAIM_NAME);
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = jwtAuthenticationConverter.convert(jwt);
            List<String> roles = jwt.getClaimAsStringList(ROLES_LIST);

            return Stream.concat(authorities.stream(), roles.stream()
                            .filter(role -> role.startsWith(ROLE_PREFIX))
                            .map(SimpleGrantedAuthority::new)
                            .map(GrantedAuthority.class::cast))
                    .toList();
        });
        return converter;
    }
}
