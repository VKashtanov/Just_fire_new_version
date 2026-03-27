package ru.kashtanov.just_fire_service.dto.liferay_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Viktor Кashtanov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GratitudeSendResponseDto {
    private String message;
    private String status;
}