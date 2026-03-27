package ru.kashtanov.just_fire_service.dto.liferay_dto;

import lombok.Data;

/**
 * @author Viktor Кashtanov
 */
@Data
public class UserResponseDto {
    private String firstName;
    private String lastName;
    private String portraitUrl;
    private String fullName;
    private String position;
    private String userId;
    private String email;
}