package ru.kashtanov.just_fire_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Viktor Кashtanov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequestDto {
    private String query;
    private int page = 0;
    private int size = 10;
    private String sortBy = "fullName";
    private String sortDirection = "asc";
    private Long siteId;
    private Long currentUserId;
}
