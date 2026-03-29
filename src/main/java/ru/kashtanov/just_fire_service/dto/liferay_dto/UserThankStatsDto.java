package ru.kashtanov.just_fire_service.dto.liferay_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Viktor Кashtanov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserThankStatsDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String position;
    private String portraitUrl;
    private int thankedQty;
    private int wasThankedQty;
}