package ru.kashtanov.just_fire_service.service.impl;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import ru.kashtanov.just_fire_service.configs.LiferayConfigs;
import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.dto.request.SearchUserRequest;

import java.util.*;

@Service
public class LiferayUserService {
    private final LiferayConfigs liferayConfigs;

    public LiferayUserService(LiferayConfigs liferayConfigs) {
        this.liferayConfigs = liferayConfigs;
    }


    public Optional<UserDto> fetchUserById(Long userId) {
        String url = liferayConfigs.getUrl() + "/o/util-incomand-api/users/" + userId;
        try {
            ResponseEntity<UserDto> response = liferayConfigs.liferayRestTemplate()
                    .getForEntity(url, UserDto.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Optional.of(response.getBody());
            }
            return Optional.empty();

        } catch (RestClientException e) {
            return Optional.empty();
        }
    }


    public List<UserDto> searchUsers(SearchUserRequest searchUserRequest) {
        String url = liferayConfigs.getUrl() + "/o/util-incomand-api/users/_search";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SearchUserRequest> request = new HttpEntity<>(searchUserRequest, headers);

        try {
            ResponseEntity<UserDto[]> response = liferayConfigs.liferayRestTemplate()
                    .postForEntity(url, request, UserDto[].class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }
        } catch (RestClientException e) {
            // TODO log
        }
        return Collections.emptyList();
    }
}