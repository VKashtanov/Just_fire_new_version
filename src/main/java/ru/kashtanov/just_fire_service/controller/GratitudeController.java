package ru.kashtanov.just_fire_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kashtanov.just_fire_service.dto.GratitudeDto;
import ru.kashtanov.just_fire_service.dto.GratitudeSaveDto;
import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.dto.request.SearchUserRequest;
import ru.kashtanov.just_fire_service.model.Gratitude;
import ru.kashtanov.just_fire_service.service.GratitudeService;
import ru.kashtanov.just_fire_service.service.impl.GratitudeServiceImpl;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("/api/gratitudes")
public class GratitudeController {
private final GratitudeService gratitudeService;

    public GratitudeController(GratitudeService gratitudeService) {
        this.gratitudeService = gratitudeService;
    }


    @PostMapping
    public String createGratitude(@RequestBody GratitudeSaveDto request) {
        Gratitude gratitude = gratitudeService.saveGratitude(request);

        return "users";
    }
}
