package ru.kashtanov.just_fire_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.just_fire_service.dto.GratitudeDto;
import ru.kashtanov.just_fire_service.dto.GratitudeSaveDto;
import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.dto.request.SearchUserRequest;
import ru.kashtanov.just_fire_service.model.Gratitude;
import ru.kashtanov.just_fire_service.service.GratitudeService;
import ru.kashtanov.just_fire_service.service.impl.GratitudeServiceImpl;

import java.net.URI;
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
    public ResponseEntity<GratitudeDto> createGratitude(@RequestBody GratitudeSaveDto request) {
        Gratitude gratitude = gratitudeService.saveGratitude(request);
        GratitudeDto dto = gratitudeService.convertToDto(gratitude);
        URI location = URI.create("/api/gratitudes/" + dto.getId());
        return ResponseEntity
                .created(location)
                .body(dto);
    }

    @GetMapping("/{userId}")
    public Gratitude findGratitude(@PathVariable Long userId) {
        Gratitude gratitudeById = gratitudeService.findGratitudeById(userId);
        System.out.println("find gratitude by id: " + gratitudeById);

        return gratitudeService.findGratitudeById(userId);

    }
}
