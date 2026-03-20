package ru.kashtanov.just_fire_service.service;

import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.model.User;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
public interface UserService {
    public abstract User findUserById(Long userId);

    public abstract User convertDtoToUser(UserDto dto);

    public abstract User createUser(User user);

    public abstract List<User> searchUsersByKeyword(String keyword);

    public abstract UserDto buildUserDto(User user);
}
