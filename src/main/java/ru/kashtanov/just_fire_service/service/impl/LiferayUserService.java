package ru.kashtanov.just_fire_service.service.impl;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import ru.kashtanov.just_fire_service.configs.LiferayConfigs;

import ru.kashtanov.just_fire_service.dto.GratitudeSaveDto;
import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.dto.liferay_dto.GratitudeSendRequestDto;
import ru.kashtanov.just_fire_service.dto.liferay_dto.UserResponseDto;
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
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    // ===========  FOR INCOMAND ==========================
    public List<UserDto> searchUsersByKeyword(String keyword) {
        SearchUserRequest request = new SearchUserRequest();
        request.setKeyword(keyword);
        request.setCompanyId(20097L);
        request.setLimit(20);
        request.setOffset(0);
        return searchUsers(request);
    }

    public UserResponseDto convertToResponseDto(UserDto dto) {
        var response = new UserResponseDto();
        response.setFirstName(dto.getFirstName());
        response.setLastName(dto.getLastName());
        response.setPortraitUrl(dto.getPortraitUrl());
        response.setFullName(dto.getFullName());
        response.setPosition(dto.getPosition());
        response.setUserId(String.valueOf(dto.getUserId()));
        response.setEmail(dto.getEmail());
        return response;
    }

    public GratitudeSaveDto convertToSaveDto(GratitudeSendRequestDto request) {
        var dto =  new GratitudeSaveDto();

        dto.setAuthorId(request.getSenderUserId());

        List<Long> recipientIds = request.getRecipients().stream()
                .map(r -> Long.parseLong(r.getUserId()))
                .toList();
        dto.setRecipientsIds(recipientIds);


        dto.setContent(request.getComment());
        return dto;
    }
}