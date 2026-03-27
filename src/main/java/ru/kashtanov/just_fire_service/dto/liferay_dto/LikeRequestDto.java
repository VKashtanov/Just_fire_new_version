package ru.kashtanov.just_fire_service.dto.liferay_dto;

import lombok.Data;

/**
 * @author Viktor Кashtanov
 */
@Data
public class LikeRequestDto {
    private long articleId;
    private long siteId;
    private long userId;
    private long companyId;

}
