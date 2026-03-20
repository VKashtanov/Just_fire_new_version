package ru.kashtanov.just_fire_service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.exception.UserNotFoundException;
import ru.kashtanov.just_fire_service.model.Gratitude;
import ru.kashtanov.just_fire_service.model.User;
import ru.kashtanov.just_fire_service.repository.UserRepo;
import ru.kashtanov.just_fire_service.service.UserService;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public User createUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User findUserById(Long userId) {
        int id = userId.intValue();
        Optional<User> byId = userRepo.findById(id);
        return byId.orElseThrow(() -> new UserNotFoundException("Пользователь не найден id: " + userId));
    }

    @Override
    public List<User> searchUsersByKeyword(String keyword) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("username").descending());
        return userRepo.findByFullNameContains(keyword, pageable);
    }

    @Override
    public UserDto buildUserDto(User user) {
        UserDto authorDto = new UserDto();
        authorDto.setUserId(user.getId());
        authorDto.setFirstName(user.getFirstName());
        authorDto.setLastName(user.getLastName());
        authorDto.setMiddleName(user.getMiddleName());
        authorDto.setFullName(user.getFullName());
        authorDto.setEmail(user.getEmail());
        authorDto.setPosition(user.getPosition());
        authorDto.setPortraitUrl(user.getPortraitUrl());
        authorDto.setPhone(user.getPhone() != null ? user.getPhone() : "");
        return authorDto;
    }

    @Override
    public User convertDtoToUser(UserDto dto) {
        var user = new User();
        user.setUserIdFromCommonDb(dto.getUserId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setMiddleName(dto.getMiddleName());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPosition(dto.getPosition());
        user.setPortraitUrl(dto.getPortraitUrl());
        user.setPhone(dto.getPhone());
        return user;
    }

}
