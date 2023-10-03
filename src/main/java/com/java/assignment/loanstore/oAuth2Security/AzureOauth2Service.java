package com.java.assignment.loanstore.oAuth2Security;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class AzureOauth2Service {
    public String generateToken() {
        String accessToken;
        String client_id = "4e5eccbd-5bfe-4c0a-91ad-e375f61fb35d";
        String client_secret = "awH8Q~L_FTCFwO0JJ3YCFp~2fkNCX7no75gwub1e";
        String grant_type = "client_credentials";
        String scope = "api://4e5eccbd-5bfe-4c0a-91ad-e375f61fb35d/.default";
        String url = "https://login.microsoftonline.com/17780279-8711-444f-a2c8-0e948eeaa315/oauth2/v2.0/token";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

//        --------------------------------------********************--------------------------------

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("grant_type", grant_type);
        map.add("client_id", client_id);
        map.add("client_secret", client_secret);
        map.add("scope", scope);
//                                      OR
//        String body = String.format("grant_type=%s&client_id=%s&client_secret=%s&scope=%s", grant_type, client_id, client_secret, scope);

//        --------------------------------------********************--------------------------------


        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        accessToken = response.getBody();
        String[] result = accessToken.split(",");
        return result[2];
    }
}


