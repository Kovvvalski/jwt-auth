package by.kovalski.jwtauth.service;

import by.kovalski.jwtauth.dto.UserDataDto;
import by.kovalski.jwtauth.entity.User;
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
        return mapToDto(userDataRepository.findById(id).
                orElseThrow(() -> new ServiceException("User data not found")));
    }

    public UserDataDto create(UserDataDto userDataDto) {
        userDataDto.setId(null);
        UserData userData = mapToEntity(userDataDto);
        return mapToDto(userDataRepository.save(userData));
    }

    public UserDataDto updateById(Long id, UserDataDto userDataDto) {
        UserData userData = mapToEntity(userDataDto);
        UserData entityToUpdate = userDataRepository.findById(id).
                orElseThrow(() -> new ServiceException("User data with id " + id + " not found"));

        entityToUpdate.setSomeUserData(userDataDto.getSomeUserData());
        entityToUpdate.setUser(userData.getUser());
        return mapToDto(userDataRepository.save(entityToUpdate));
    }

    public UserDataDto deleteById(Long id) {
        if (userDataRepository.existsById(id)) {
            return mapToDto(userDataRepository.removeUserDataById(id));
        }
        throw new ServiceException("User data with id " + id + " not found");
    }

    private UserDataDto mapToDto(UserData userData) {
        return new UserDataDto(userData.getId(), userData.getSomeUserData(),
                userData.getUser() == null ? null : userData.getUser().getId());
    }

    private UserData mapToEntity(UserDataDto userDataDto) {
        return new UserData(userDataDto.getUserId(), userDataDto.getSomeUserData(),
                new User(userDataDto.getUserId(), null, null, null));
    }
}
