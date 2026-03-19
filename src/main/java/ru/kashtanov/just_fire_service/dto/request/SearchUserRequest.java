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
public class SearchUserRequest {
    private Long companyId;
    private String keyword;
    private Integer limit;
    private Integer offset;
}
