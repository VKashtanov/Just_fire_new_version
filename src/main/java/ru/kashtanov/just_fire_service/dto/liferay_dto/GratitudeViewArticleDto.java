package ru.kashtanov.just_fire_service.dto.liferay_dto;

import lombok.Data;
import ru.kashtanov.just_fire_service.dto.UserDto;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Data
public class GratitudeViewArticleDto {
    private long articleId;
    private String articleText;
    private Long timestamp;
    private UserDto author;
    private List<UserDto> recipients;
    private Long likeQty;
}