package ru.kashtanov.just_fire_service.dto.liferay_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Viktor Кashtanov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopThankerUserDto {
    @JsonProperty("userId")
    private String userId;

    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String portraitUrl;
    private String position;

    @JsonProperty("thankedQty")
    private Long thankedQty;

    @JsonProperty("wasThankedQty")
    private Long wasThankedQty;





}