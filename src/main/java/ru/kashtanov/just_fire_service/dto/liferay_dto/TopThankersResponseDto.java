package ru.kashtanov.just_fire_service.dto.liferay_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopThankersResponseDto {
    private String statsType;
    private List<TopThankerUserDto> users;
    private String status;
}