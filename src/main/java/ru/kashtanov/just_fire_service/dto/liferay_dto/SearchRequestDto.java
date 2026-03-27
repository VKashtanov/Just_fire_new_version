package ru.kashtanov.just_fire_service.dto.liferay_dto;

import lombok.Data;

/**
 * @author Viktor Кashtanov
 */
@Data
public class SearchRequestDto {
    private String query;
    private String searchType;
    private StructuredSearchDto structured;
    private Long companyId;
    private Long currentUserId;
    private Long siteId;
}
