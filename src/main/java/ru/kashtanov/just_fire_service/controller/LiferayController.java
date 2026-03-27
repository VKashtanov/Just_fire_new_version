package ru.kashtanov.just_fire_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kashtanov.just_fire_service.components.GratitudeToLiferayMapper;
import ru.kashtanov.just_fire_service.dto.*;
import ru.kashtanov.just_fire_service.dto.liferay_dto.*;
import ru.kashtanov.just_fire_service.service.GratitudeService;
import ru.kashtanov.just_fire_service.service.LikeService;
import ru.kashtanov.just_fire_service.service.impl.LiferayUserService;
import ru.kashtanov.just_fire_service.service.impl.LikeServiceImpl;

import java.net.URI;
import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("/api/gratitudes")
public class LiferayController {

    private final GratitudeService gratitudeService;
    private final GratitudeToLiferayMapper mapper;
    private final LiferayUserService liferayUserService;
    private final LikeServiceImpl likeService;

    public LiferayController(GratitudeService gratitudeService, GratitudeToLiferayMapper mapper, LiferayUserService liferayUserService, LikeServiceImpl likeService) {
        this.gratitudeService = gratitudeService;
        this.mapper = mapper;
        this.liferayUserService = liferayUserService;
        this.likeService = likeService;
    }

    @PostMapping("/send")
    public ResponseEntity<GratitudeSendResponseDto> sendGratitude(@RequestBody GratitudeSendRequestDto request) {
        GratitudeSaveDto saveDto = liferayUserService.convertToSaveDto(request);
        gratitudeService.saveGratitude(saveDto);
        var dto = new GratitudeSendResponseDto();
        dto.setMessage("Gratitude successfully sent");
        dto.setStatus("success");
        URI location = URI.create("/api/gratitudes/send");
        return ResponseEntity
                .created(location)
                .body(dto);
    }

    @PostMapping
    public ResponseEntity<GratitudePageResponse> getGratitudes(@RequestBody GratitudesRequestDto request) {
        int page = request.getPageAsInt();
        int pageSize = request.getPageSizeAsInt();
        int offset = (page - 1) * pageSize;
        String filter = request.getFilter();
        Long userId = request.getUserId();
        List<GratitudeDto> allGratitudes = gratitudeService.findAllGratitudes(userId,pageSize, offset,filter);
        List<GratitudeDto> nextPage = gratitudeService.findAllGratitudes(userId,pageSize, offset + pageSize,filter);
        boolean hasMore = !nextPage.isEmpty();

        GratitudePageResponse dto = mapper.toPageResponse(allGratitudes, page, pageSize, hasMore);
        URI location = URI.create("/api/gratitudes");
        return ResponseEntity
                .created(location)
                .body(dto);
    }

    @PostMapping("/search")
    public List<UserResponseDto> searchUsers(@RequestBody SearchRequestDto request) {
        List<UserDto> users = liferayUserService.searchUsersByKeyword(request.getQuery());
        return users.stream()
                .map(liferayUserService::convertToResponseDto)
                .toList();
    }

    @PostMapping("/like")
    public ResponseEntity<LikeResponseDto> handleLikeClick(@RequestBody LikeRequestDto request) {
        LikeSaveDto saveDto = mapper.convertToLikeSaveDto(request);
        LikeInfoDto likeInfoDto = likeService.toggleLike(saveDto);
        LikeResponseDto dto = mapper.convertToLikeResponseDto(likeInfoDto);
        URI location = URI.create("/api/gratitudes/like");
        return ResponseEntity
                .created(location)
                .body(dto);
    }
}



