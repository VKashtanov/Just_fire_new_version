package ru.kashtanov.just_fire_service.dto.liferay_dto;

import lombok.Data;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Data
public class GratitudePageResponse {
    private List<GratitudeViewArticleDto> gratitudes;
    private boolean hasMore;
    private int pageSize;
    private int page;
    private String status;
}