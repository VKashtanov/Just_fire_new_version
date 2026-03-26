package ru.kashtanov.just_fire_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.just_fire_service.dto.GratitudeDto;
import ru.kashtanov.just_fire_service.dto.GratitudeSaveDto;
import ru.kashtanov.just_fire_service.service.GratitudeService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
        GratitudeDto dto = gratitudeService.createGratitude(request);
        URI location = URI.create("/api/gratitudes/" + dto.getId());
        return ResponseEntity
                .created(location)
                .body(dto);
    }

    @GetMapping("/{gratitudeId}")
    public ResponseEntity<GratitudeDto> findGratitude(@PathVariable Long gratitudeId) {
        Optional<GratitudeDto> gratitudeById = gratitudeService.findGratitudeById(gratitudeId);
        return gratitudeById.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping()
    public List<GratitudeDto> getAll(@RequestParam(defaultValue = "20") int limit,
                                           @RequestParam(defaultValue = "0") int offset)
    {
        return gratitudeService.findAllGratitudes(limit, offset);
    }
}
