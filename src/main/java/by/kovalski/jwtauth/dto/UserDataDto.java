package by.kovalski.jwtauth.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDataDto {
    private Long id;
    private String someUserData;
    private String userId;
}
