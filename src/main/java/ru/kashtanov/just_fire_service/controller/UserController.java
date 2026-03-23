package ru.kashtanov.just_fire_service.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;
import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.dto.request.SearchUserRequest;
import ru.kashtanov.just_fire_service.dto.response.ErrorResponse;
import ru.kashtanov.just_fire_service.service.impl.LiferayUserService;

import java.time.LocalDateTime;
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
    public ResponseEntity<List<UserDto>> searchUsers( @Valid @RequestBody SearchUserRequest request) {
        return liferayUserService.searchUsers(request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        System.out.println("Error: " + ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(400)
                        .error("Bad Request")
                        .message(ex.getMessage())
                        .build());
    }

}
