package ru.kashtanov.just_fire_service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.dto.request.SearchUserRequest;
import ru.kashtanov.just_fire_service.exception.UserNotFoundException;
import ru.kashtanov.just_fire_service.model.User;
import ru.kashtanov.just_fire_service.repository.UserRepo;
import ru.kashtanov.just_fire_service.service.UserService;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final LiferayUserService liferayUserService;

    public UserServiceImpl(UserRepo userRepo, LiferayUserService liferayUserService) {
        this.userRepo = userRepo;
        this.liferayUserService = liferayUserService;
    }

    public User createUser(User user) {
        return userRepo.save(user);
    }

    public User getOrCreate(Long userId) {

        return findUserByCommonDbId(userId)
                .orElseGet(() -> {
                    UserDto userDto = liferayUserService.fetchUserById(userId)
                            .orElseThrow(() -> new UserNotFoundException("User not found in Liferay: " + userId));
                    User newUser = convertDtoToUser(userDto);
                    return createUser(newUser);
                });


    }

    @Override
    public UserDto getOrCreateUserDto(Long userId) {
        User user = getOrCreate(userId);
        return buildUserDto(user);
    }


    @Override
    public Optional<User> findUserByCommonDbId(Long userId) {
        return userRepo.findUserByCommonDbId(userId);
    }


    @Override
    public User findUserById(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден id: " + userId));
    }

    @Override
    public List<UserDto> searchUsersByKeyword(SearchUserRequest request) {
        return liferayUserService.searchUsers(request);
    }


    @Override
    public UserDto buildUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setMiddleName(user.getMiddleName());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPosition(user.getPosition());
        dto.setPortraitUrl(user.getPortraitUrl());
        dto.setPhone(user.getPhone() != null ? user.getPhone() : "");
        return dto;
    }


    @Override
    public User convertDtoToUser(UserDto dto) {
        User user = new User();
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