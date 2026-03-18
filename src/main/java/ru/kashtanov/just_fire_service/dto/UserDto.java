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
public class UserDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String email;
    private String position;
    private String portraitUrl;
    private String phone = "";
}
