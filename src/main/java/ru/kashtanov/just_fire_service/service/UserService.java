package ru.kashtanov.just_fire_service.service;

import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.dto.liferay_dto.TopThankersResponseDto;
import ru.kashtanov.just_fire_service.dto.request.SearchUserRequest;
import ru.kashtanov.just_fire_service.model.User;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */
public interface UserService {
    public abstract List<UserDto> findUsers(Pageable pageable);

    public abstract TopThankersResponseDto findThankGivers(int limit, int offset);

    public abstract TopThankersResponseDto findThanksReceivers(int limit, int offset);

    public abstract List<UserDto> searchUsersByKeyword(SearchUserRequest request);

    public abstract UserDto getOrCreateUserDto(Long userId);

    public abstract User getOrCreate(Long userId);

    public abstract User findUserById(Long userId);

    public abstract Optional<User> findUserByCommonDbId(Long userId);

    public abstract User convertDtoToUser(UserDto dto);

    public abstract User createUser(User user);

    public abstract UserDto buildUserDto(User user);
}
