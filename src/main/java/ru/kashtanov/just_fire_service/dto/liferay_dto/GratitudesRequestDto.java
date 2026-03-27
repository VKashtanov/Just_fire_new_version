package ru.kashtanov.just_fire_service.dto.liferay_dto;

import lombok.Data;

/**
 * @author Viktor Кashtanov
 */
@Data
public class GratitudesRequestDto {
    private String siteId;
    private Integer page;
    private Integer pageSize;
    private String filter;
    private String userId;

    public int getPageAsInt() {
        return page != null ?  page : 1;
    }

    public int getPageSizeAsInt() {
        return pageSize != null ? pageSize : 5;
    }

    public long getSiteIdAsLong() {
        return siteId != null ? Long.parseLong(siteId) : 0;
    }

    public long getUserIdAsLong() {
        return userId != null ? Long.parseLong(userId) : 0;
    }
}