package ru.kashtanov.just_fire_service.dto.liferay_dto;

import lombok.Data;

/**
 * @author Viktor Кashtanov
 */
@Data
public class RecipientDto {
    private String userId;
    private String userName;
    private String userEmail;
}