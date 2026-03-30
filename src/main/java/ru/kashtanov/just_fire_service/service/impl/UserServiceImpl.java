package ru.kashtanov.just_fire_service.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Service;
import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.dto.liferay_dto.TopThankerUserDto;
import ru.kashtanov.just_fire_service.dto.liferay_dto.TopThankersResponseDto;
import ru.kashtanov.just_fire_service.dto.liferay_dto.UserThankStatsDto;
import ru.kashtanov.just_fire_service.dto.request.SearchUserRequest;
import ru.kashtanov.just_fire_service.exception.UserNotFoundException;
import ru.kashtanov.just_fire_service.model.User;
import ru.kashtanov.just_fire_service.repository.UserRepo;
import ru.kashtanov.just_fire_service.service.UserService;

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
                            .orElseThrow(() -> new UserNotFoundException("User is not found in Incomand DB with id: " + userId));
                    User newUser = convertDtoToUser(userDto);
                    return createUser(newUser);
                });
    }


    public TopThankersResponseDto findThankGivers(int limit, int offset) {
        List<TopThankerUserDto> stats = userRepo.findTopThanksGivers(limit, offset);
        var responseDto = new TopThankersResponseDto();
        List<TopThankerUserDto> list = stats.stream()
                .map(UserServiceImpl::rebuild)
                .toList();
        responseDto.setUsers(list);
        responseDto.setStatus("success");
        responseDto.setStatsType("top-thankers");
        return responseDto;

    }


    public TopThankersResponseDto findThanksReceivers(int limit, int offset) {
        List<TopThankerUserDto> stats = userRepo.findTopGratitudeRecipients(limit, offset);
        List<TopThankerUserDto> list = stats.stream()
                .map(UserServiceImpl::rebuild)
                .toList();
        var responseDto = new TopThankersResponseDto();
        responseDto.setStatsType("top-recipients");  // ← другой тип!
        responseDto.setUsers(list);
        responseDto.setStatus("success");

        return responseDto;
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

    private static TopThankerUserDto rebuild(TopThankerUserDto s) {
        return new TopThankerUserDto(
                s.getUserId(),
                s.getFirstName(),
                s.getLastName(),
                s.getFullName(),
                s.getEmail(),
                s.getPosition(),
                s.getPortraitUrl(),
                s.getThankedQty(),
                s.getWasThankedQty());
    }
}