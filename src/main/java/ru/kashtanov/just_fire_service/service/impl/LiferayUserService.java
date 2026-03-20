package ru.kashtanov.just_fire_service.service.impl;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import ru.kashtanov.just_fire_service.configs.LiferayConfigs;
import ru.kashtanov.just_fire_service.dto.LiferayUserDto;
import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.dto.request.SearchUserRequest;
import ru.kashtanov.just_fire_service.model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Service
public class LiferayUserService {
    private final LiferayConfigs liferayConfigs;

    public LiferayUserService(LiferayConfigs liferayConfigs) {
        this.liferayConfigs = liferayConfigs;
    }


    public  ResponseEntity<UserDto> findUserById(Long userId) {
        String incomandUrl = liferayConfigs.getUrl();
        String url = incomandUrl + "/api/jsonws/user/get-user-by-id/user-id/" + userId;
        try {
            ResponseEntity<LiferayUserDto> response = liferayConfigs.liferayRestTemplate()
                    .getForEntity(url, LiferayUserDto.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                UserDto userDto = convertToUserDto(response.getBody());
                return ResponseEntity.ok(userDto);
            }
            return ResponseEntity.notFound().build();

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity<List<UserDto>> searchUsers(SearchUserRequest searchUserRequest) {
        String url = liferayConfigs.getUrl() + "/o/util-incomand-api/users/_search";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Wrap Dto to Http Entity . Dto to request body
        HttpEntity<SearchUserRequest> request = new HttpEntity<>(searchUserRequest, headers);
        try {
            ResponseEntity<UserDto[]> response = liferayConfigs.liferayRestTemplate()
                    .postForEntity(url, request, UserDto[].class);
            System.out.println(response.getBody());
            return ResponseEntity.ok(Arrays.asList(response.getBody()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    public UserDto convertToUserDto(LiferayUserDto dto) {
        var userDto = new UserDto();
        if (dto == null) return userDto;
        userDto.setUserId(Long.parseLong(dto.getUserId()));
        userDto.setFirstName(dto.getFirstName());
        userDto.setMiddleName(dto.getMiddleName());
        userDto.setLastName(dto.getLastName());
        userDto.setFullName(dto.getFullName());
        userDto.setEmail(dto.getEmailAddress());
        userDto.setPosition(dto.getJobTitle());
        userDto.setPortraitUrl(dto.getPortraitId());
        userDto.setPhone("");
        return userDto;
    }

    public User convertDtoToUser(LiferayUserDto dto) {
        var user = new User();
        user.setUserIdFromCommonDb(Long.valueOf(dto.getUserId()));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setMiddleName(dto.getMiddleName());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmailAddress());
        user.setPosition(dto.getJobTitle());
        user.setPortraitUrl(dto.getPortraitId()); //todo
        user.setPhone(dto.getPhone());
        return user;
    }


    public void createUser(UserDto user) {
        String url = "http://liferay.com/api/users";
        liferayConfigs.liferayRestTemplate().postForObject(url, user, String.class);
    }
}
