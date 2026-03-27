package ru.kashtanov.just_fire_service.dto.liferay_dto;

import lombok.Data;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Data
public class GratitudeSendRequestDto {
    private List<RecipientDto> recipients;
    private String comment;
    private Long siteId;
    private Long companyId;
    private Long senderUserId;
}