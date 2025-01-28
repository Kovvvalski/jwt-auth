package by.kovalski.jwtauth.config;

import by.kovalski.jwtauth.filter.JwtAuthFilter;
import by.kovalski.jwtauth.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthenticationFilter;
    private final UserService userService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /////////////////////////////////////////////////////////////////////////////////////////////////////
//        Customizer<CsrfConfigurer<HttpSecurity>> csrfCustomizer = new Customizer<>() {
//            @Override
//            public void customize(CsrfConfigurer<HttpSecurity> configurer) {
//                configurer.disable();
//            }
//        };
//        http.csrf(csrfCustomizer);
//
//        Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer = new Customizer<>() {
//            @Override
//            public void customize(CorsConfigurer<HttpSecurity> configurer) {
//                CorsConfigurationSource configurationSource = new CorsConfigurationSource() {
//                    @Override
//                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                        CorsConfiguration corsConfiguration = new CorsConfiguration();
//                        corsConfiguration.setAllowedOriginPatterns(List.of("*"));
//                        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//                        corsConfiguration.setAllowedHeaders(List.of("*"));
//                        corsConfiguration.setAllowCredentials(true);
//                        return corsConfiguration;
//                    }
//                };
//                configurer.configurationSource(configurationSource);
//            }
//        };
//        http.cors(corsCustomizer);
//
//        Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.
//                AuthorizationManagerRequestMatcherRegistry> authorizeCustomizer = new Customizer<>() {
//            @Override
//            public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.
//                                          AuthorizationManagerRequestMatcherRegistry registry) {
//
//                registry.requestMatchers("/auth/**").permitAll();
//                registry.requestMatchers("/test/**").permitAll();
//                registry.requestMatchers("/data_management/**").hasRole("ADMIN");
//                registry.requestMatchers("/user_management/**").hasAnyRole("ADMIN", "USER");
//                registry.anyRequest().authenticated();
//            }
//        };
//        http.authorizeHttpRequests(authorizeCustomizer);
//
//        Customizer<SessionManagementConfigurer<HttpSecurity>> sessionCustomizer = new Customizer<>() {
//            @Override
//            public void customize(SessionManagementConfigurer<HttpSecurity> configurer) {
//                configurer.sessionCreationPolicy(STATELESS);
//            }
//        };
//        http.sessionManagement(sessionCustomizer);


        /////////////////////////////////////////////////////////////////////////////////////////////////////
        // EQUAL TO
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(cors -> cors.configurationSource(request -> {
            var corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOriginPatterns(List.of("*"));
            corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            corsConfiguration.setAllowedHeaders(List.of("*"));
            corsConfiguration.setAllowCredentials(true);
            return corsConfiguration;
        }));
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/test/**").permitAll()
                .requestMatchers("/data_management/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated());

        http.sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS));
        /////////////////////////////////////////////////////////////////////////////////////////////////////

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
