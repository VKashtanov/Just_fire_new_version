package ru.kashtanov.just_fire_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.kashtanov.just_fire_service.configs.LiferayConfigs;
import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.dto.request.SearchUserRequest;

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


    public String getUser(Long userId) {
        String incomandUrl = liferayConfigs.getUrl();
        String url = incomandUrl + "/api/jsonws/user/get-user-by-id/user-id/" + userId;
        return liferayConfigs.liferayRestTemplate().getForObject(url, String.class); // todo object here not String
    }


    public List<UserDto> searchUsers(SearchUserRequest searchUserRequest) {
        String url = liferayConfigs.getUrl() + "/o/util-incomand-api/users/_search";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Wrap Dto to Http Entity . Dto to request body
        HttpEntity<SearchUserRequest> request = new HttpEntity<>(searchUserRequest, headers);
        try {
            ResponseEntity<UserDto[]> response = liferayConfigs.liferayRestTemplate()
                    .postForEntity(url, request, UserDto[].class);
            System.out.println(response.getBody());
            return Arrays.asList(response.getBody());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }



    public void createUser(UserDto user) {
        String url = "http://liferay.com/api/users";
        liferayConfigs.liferayRestTemplate().postForObject(url, user, String.class);
    }
}
