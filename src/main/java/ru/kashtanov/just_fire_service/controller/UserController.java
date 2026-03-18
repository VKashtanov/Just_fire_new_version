package ru.kashtanov.just_fire_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.dto.request.SearchUserRequest;
import ru.kashtanov.just_fire_service.service.impl.LiferayUserService;

import java.util.Collections;
import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private LiferayUserService liferayUserService;  // Только сервис!

    @GetMapping("/{userId}")
    public ResponseEntity<String> getUser(@PathVariable Long userId) {
        String user = liferayUserService.getUser(userId);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/_search")
    public List<UserDto> searchUsers(@RequestBody SearchUserRequest searchUserRequest) {
        List<UserDto> users = liferayUserService.searchUsers(searchUserRequest);
        return users;
    }
}
