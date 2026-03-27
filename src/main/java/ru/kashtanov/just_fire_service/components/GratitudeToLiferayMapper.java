package ru.kashtanov.just_fire_service.components;

import org.springframework.stereotype.Component;
import ru.kashtanov.just_fire_service.dto.GratitudeDto;
import ru.kashtanov.just_fire_service.dto.LikeInfoDto;
import ru.kashtanov.just_fire_service.dto.LikeSaveDto;
import ru.kashtanov.just_fire_service.dto.liferay_dto.GratitudePageResponse;
import ru.kashtanov.just_fire_service.dto.liferay_dto.GratitudeViewArticleDto;
import ru.kashtanov.just_fire_service.dto.liferay_dto.LikeRequestDto;
import ru.kashtanov.just_fire_service.dto.liferay_dto.LikeResponseDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Viktor Кashtanov
 */

@Component
public class GratitudeToLiferayMapper {

    public GratitudeViewArticleDto toViewArticleDto(GratitudeDto dto) {
        GratitudeViewArticleDto viewDto = new GratitudeViewArticleDto();

        viewDto.setArticleId(dto.getId());
        viewDto.setArticleText(dto.getContent());
        viewDto.setTimestamp(dto.getTimestamp());
        viewDto.setAuthor(dto.getAuthor());
        viewDto.setRecipients(dto.getRecipients());
        viewDto.setLikeQty((long) dto.getLikesCount());

        return viewDto;
    }

    public List<GratitudeViewArticleDto> toViewArticleDtoList(List<GratitudeDto> dtos) {
        return dtos.stream()
                .map(this::toViewArticleDto)
                .collect(Collectors.toList());
    }

    public GratitudePageResponse toPageResponse(List<GratitudeDto> dtos, int page, int pageSize, boolean hasMore) {
        var response = new GratitudePageResponse();

        response.setGratitudes(toViewArticleDtoList(dtos));
        response.setHasMore(hasMore);
        response.setPageSize(pageSize);
        response.setPage(page);
        response.setStatus("success");

        return response;
    }

    public LikeSaveDto convertToLikeSaveDto(LikeRequestDto dto) {
        var saveDto = new LikeSaveDto();
        saveDto.setGratitudeId(dto.getArticleId());
        saveDto.setUserId(dto.getUserId());
        return saveDto;
    }

    public LikeResponseDto convertToLikeResponseDto(LikeInfoDto dto) {
        var responseDto = new LikeResponseDto();
        responseDto.setStatus("success");
        responseDto.setLikeCount(dto.getLikeCount());
        responseDto.setUserLiked(dto.isLikedByCurrentUser());
        responseDto.setMessage(dto.getLikedUsers().toString());
        return responseDto;
    }
}
