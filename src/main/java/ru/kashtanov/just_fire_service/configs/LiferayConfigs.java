package ru.kashtanov.just_fire_service.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

/**
 * @author Viktor Кashtanov
 */

@Getter
@Configuration
public class LiferayConfigs {
    @Value("${INCOMAND_URL}")
    private String url;
    @Value("${INCOMAND_USERNAME}")
    private String username;
    @Value("${INCOMAND_PASSWORD}")
    private String password;

    @Bean
    public RestTemplate liferayRestTemplate() {
        var restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add((request, body, execution) -> {
            String auth = username + ":" + password; // it's required for BasicAuth login:password
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeader = "Basic " + new String(encodedAuth);
            request.getHeaders().add("Authorization", authHeader);
            return execution.execute(request, body);
        });

        return restTemplate;
    }


}
