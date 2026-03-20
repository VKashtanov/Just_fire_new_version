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
public class GratitudeSaveDto {
    private Long authorId;
    private List<Long> recipientsIds;
    private String content;
}
