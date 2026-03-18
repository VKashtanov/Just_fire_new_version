package ru.kashtanov.just_fire_service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.kashtanov.just_fire_service.model.User;
import ru.kashtanov.just_fire_service.repository.UserRepo;
import ru.kashtanov.just_fire_service.service.UserService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User getByUserId(Long userId) {
        return null;
    }

    @Override
    public List<User> searchUsersByKeyword(String keyword) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("username").descending());
        return userRepo.findByFullNameContains(keyword, pageable);
    }
}
