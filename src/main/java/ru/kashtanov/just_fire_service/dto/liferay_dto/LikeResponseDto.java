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
public class LikeResponseDto {
    private Long likeCount;
    private String message;
    private boolean userLiked;
    private String status;
}