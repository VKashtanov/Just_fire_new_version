package ru.kashtanov.just_fire_service.dto;

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
public class LikeInfoDto {
    private Long likeCount;
    private List<UserDto> likedUsers;
    private GratitudeDto gratitude;
    private boolean likedByCurrentUser;
}
