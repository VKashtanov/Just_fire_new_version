package ru.kashtanov.just_fire_service.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.dto.request.SearchUserRequest;
import ru.kashtanov.just_fire_service.exception.UserNotFoundException;
import ru.kashtanov.just_fire_service.service.UserService;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserDto findUser(@PathVariable Long userId) {
        return userService.getOrCreateUserDto(userId);
    }


    @PostMapping("/_search")
    public List<UserDto> searchUsers(@Valid @RequestBody SearchUserRequest request) {
        return userService.searchUsersByKeyword(request);
    }


    @GetMapping("/test")
    public String test() {
        throw new UserNotFoundException("User not found in test");
    }


}
