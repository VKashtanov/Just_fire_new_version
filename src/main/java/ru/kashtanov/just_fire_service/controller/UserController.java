package ru.kashtanov.just_fire_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.dto.request.SearchUserRequest;
import ru.kashtanov.just_fire_service.service.impl.LiferayUserService;

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
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        return liferayUserService.findUserById(userId);
    }


    @PostMapping("/_search")
    public ResponseEntity<List<UserDto>> searchUsers(@RequestBody SearchUserRequest request) {
        return liferayUserService.searchUsers(request);
    }
}
