package by.kovalski.jwtauth.service;

import by.kovalski.jwtauth.dto.UserDataDto;
import by.kovalski.jwtauth.entity.UserData;
import by.kovalski.jwtauth.exception.ServiceException;
import by.kovalski.jwtauth.repository.UserDataRepository;
import by.kovalski.jwtauth.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDataService {
    private final UserDataRepository userDataRepository;
    private final UserRepository userRepository;
    public UserDataDto getById(Long id) {
        return mapToDto(userDataRepository.findById(id).
                orElseThrow(() -> new ServiceException("User data not found")));
    }

    @Transactional
    public UserDataDto create(UserDataDto userDataDto, Long userId) {
        userDataDto.setId(null);
        UserData userData = mapToEntity(userDataDto);
        userData.setUser(userRepository.findById(userId).orElseThrow(() -> new ServiceException("User with id " + userId + " not found")));
        return mapToDto(userDataRepository.save(userData));
    }

    @Transactional
    public UserDataDto updateById(Long id, UserDataDto userDataDto) {
        UserData entityToUpdate = userDataRepository.findById(id).
                orElseThrow(() -> new ServiceException("User data with id " + id + " not found"));

        entityToUpdate.setSomeUserData(userDataDto.getSomeUserData());
        return mapToDto(userDataRepository.save(entityToUpdate));
    }

    @Transactional
    public UserDataDto deleteById(Long id) {
        UserData toRemove = userDataRepository.findById(id).
                orElseThrow(() -> new ServiceException("User data with id " + id + " not found"));
        UserDataDto toRemoveDto = mapToDto(toRemove);
        userDataRepository.delete(toRemove);
        return toRemoveDto;
    }

    UserDataDto mapToDto(UserData userData) {
        return new UserDataDto(userData.getId(), userData.getSomeUserData(),
                userData.getUser() == null ? null : userData.getUser().getId());
    }

    UserData mapToEntity(UserDataDto userDataDto) {
        return new UserData(userDataDto.getUserId(), userDataDto.getSomeUserData(), null);
    }
}
