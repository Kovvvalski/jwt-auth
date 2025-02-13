package by.kovalski.jwtauth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_data")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "some_user_data", length = 500)
    private String someUserData;

    @Column(name = "userId", nullable = false)
    private String userId;
}
