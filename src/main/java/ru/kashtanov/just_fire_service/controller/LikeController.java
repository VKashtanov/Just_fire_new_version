package ru.kashtanov.just_fire_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.just_fire_service.dto.LikeInfoDto;
import ru.kashtanov.just_fire_service.dto.LikeSaveDto;
import ru.kashtanov.just_fire_service.service.impl.LikeServiceImpl;

import java.net.URI;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeServiceImpl likeService;

    public LikeController(LikeServiceImpl likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/_toggle")
    public ResponseEntity<LikeInfoDto> createGratitude(@RequestBody LikeSaveDto request) {
        LikeInfoDto likeInfoDto = likeService.toggleLike(request);
        URI location = URI.create("/api/likes/_toggle");
        return ResponseEntity.created(location).body(likeInfoDto);
    }

}
