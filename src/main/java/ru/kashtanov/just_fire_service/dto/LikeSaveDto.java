package ru.kashtanov.just_fire_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Viktor Кashtanov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeSaveDto {
    private Long userId;
    private Long gratitudeId;
    private int qtyLastLikes;
}
