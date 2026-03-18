package ru.kashtanov.just_fire_service.service;

import ru.kashtanov.just_fire_service.model.User;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
public interface UserService {
    public User getByUserId(Long userId);

    public List<User> searchUsersByKeyword(String keyword);

}
