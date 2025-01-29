package by.kovalski.jwtauth.service;

import by.kovalski.jwtauth.dto.AuthRequestDto;
import by.kovalski.jwtauth.dto.AuthResponseDto;
import by.kovalski.jwtauth.dto.UserDto;
import by.kovalski.jwtauth.entity.Role;
import by.kovalski.jwtauth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDto signUp(AuthRequestDto authRequestDto) {
        UserDto userDto = new UserDto(null, authRequestDto.getLogin(),
                passwordEncoder.encode(authRequestDto.getPassword()), Role.ROLE_USER);
        User user = userService.mapToEntity(userService.create(userDto));
        String token = jwtService.generateToken(user);
        return new AuthResponseDto(token);
    }

    public AuthResponseDto signIn(AuthRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getPassword()
        ));

        UserDetails user = userService.getByUsername(request.getLogin());
        String jwt = jwtService.generateToken(user);
        return new AuthResponseDto(jwt);
    }
}
