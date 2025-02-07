package by.kovalski.jwtauth.dto;

import by.kovalski.jwtauth.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private String login;
    private String password;
    private Role role;
}
