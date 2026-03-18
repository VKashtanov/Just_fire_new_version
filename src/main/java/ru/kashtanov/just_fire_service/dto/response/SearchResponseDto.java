package ru.kashtanov.just_fire_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kashtanov.just_fire_service.model.User;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponseDto {
    private List<User> users;
    private String message;
}
