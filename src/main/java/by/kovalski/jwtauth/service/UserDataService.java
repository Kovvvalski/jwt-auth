package by.kovalski.jwtauth.service;

import by.kovalski.jwtauth.dto.UserDataDto;
import by.kovalski.jwtauth.entity.UserData;
import by.kovalski.jwtauth.exception.ServiceException;
import by.kovalski.jwtauth.repository.UserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDataService {
    private final UserDataRepository userDataRepository;

    public UserDataDto getById(Long id) {
        return mapFrom(userDataRepository.findById(id).orElseThrow(() -> new ServiceException("User data not found")));
    }

    private UserDataDto mapFrom(UserData userData) {
        return new UserDataDto(userData.getId(), userData.getSomeUserData(), userData.getUser().getId());
    }

}
