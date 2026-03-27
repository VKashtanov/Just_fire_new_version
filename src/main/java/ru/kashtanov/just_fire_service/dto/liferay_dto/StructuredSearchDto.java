package ru.kashtanov.just_fire_service.dto.liferay_dto;

import lombok.Data;

/**
 * @author Viktor Кashtanov
 */
@Data
public class StructuredSearchDto {
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private String mode;
}