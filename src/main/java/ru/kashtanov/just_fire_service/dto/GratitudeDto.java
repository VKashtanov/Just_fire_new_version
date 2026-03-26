package ru.kashtanov.just_fire_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GratitudeDto {
    private Long id;
    private String content;
    private Long timestamp;
    private UserDto author;
    private List<UserDto> recipients;
    private int likesCount;
}
