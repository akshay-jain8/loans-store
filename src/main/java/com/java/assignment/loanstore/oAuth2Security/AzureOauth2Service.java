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

    public String getAuthorizationCode() {
        String authCode;
        String url = "https://login.microsoftonline.com/17780279-8711-444f-a2c8-0e948eeaa315/oauth2/v2.0/authorize";
        String response_type = "code";
        String client_id = "4e5eccbd-5bfe-4c0a-91ad-e375f61fb35d";
        String scope = "api://4e5eccbd-5bfe-4c0a-91ad-e375f61fb35d/.default";
        String state = "state123";
        String redirect_uri = "http://localhost:8080/login/oAuth2/code";
        String access_type = "offline";
        String prompt = "consent";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("response_type", response_type);
        map.add("client_id", client_id);
        map.add("scope", scope);
        map.add("state", state);
        map.add("redirect_uri", redirect_uri);
        map.add("access_type", access_type);
        map.add("prompt", prompt);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        authCode = response.getBody();
        return authCode;
    }

    public String getAccessToken() {
        String accessToken;
        String token;
        String url = "https://login.microsoftonline.com/17780279-8711-444f-a2c8-0e948eeaa315/oauth2/v2.0/token";
        String grant_type = "authorization_code";
        String client_id = "4e5eccbd-5bfe-4c0a-91ad-e375f61fb35d";
        String client_secret = "awH8Q~L_FTCFwO0JJ3YCFp~2fkNCX7no75gwub1e";
//        String authCode = getAuthorizationCode();
        String redirect_uri = "http://localhost:8080/login/oAuth2/code";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("grant_type", grant_type);
        map.add("client_id", client_id);
        map.add("client_secret", client_secret);
//        map.add("code", authCode);
        map.add("code", "0.AT0AeQJ4FxGHT0SiyA6UjuqjFb3MXk7-WwpMka3jdfYfs12hAOk.AgABAAIAAAAtyolDObpQQ5VtlI4uGjEPAgDs_wUA9P_aiYdSzqneFmg14p7GQfPP7IimqOu_QJY8HWQ6GHqeEe2WgU6Q8PF5PQ4nWkGpc2nRpwVW_KcUOreOkUfxUH_oF0WoyzWBoMuQw2Hn3zi3TWDSiizjiiriq_fUY206AnBOnBfC60XFKSuCcqvSJ6P0yt9PQ93lbvL1MVMys9WZZ_AxLSVsOF6gezKbp23RLXIhWjCLN3pG4TplgVdHRqC-MhvmtzfLUUoOlME4w9HZqM34XaF-2hkGR6uhwfrLU3zTtYh7SfeAsm24BAtwdpRtOvLpOiy8PhOxt7wzhvTzWWaz5IF_qlNfB8Yd9FT4I0VLld5o3427WtMtmZ5iqBEUomiKwupC70wezwsGHwEy5VapTNmsU9Sh-etHf2kJmumGi7Vrnz7a6cSMx8utQquzr4PecUMLHqJyqKWE1ZvMvZCnLyTyaAszHSVZJD9TUzfXlsGmeqIIok6Ne12vUurGpakUQUqks6mgZK6IJ1WFPvf_nM6MXhwjlZInrRfALIPWDt-5CqfJUllk846wvAIeLCHFB9ra7Y9Y4es5FbijZDAFES3iUVk09bYWqiHFL_GX3I0OeOzzs3iBP_S8GpJ_RnDH1pd2LXQWN4Es_c2-PJ2Z5mDR_IyOc6fpOS4amewfWdRkhVeNFdifdvYx3SA2G0O0ujntffHjHTYZyY-4JDdEa9omcUdgc4ZTylezVJ62EH29By7cXok_y1Mz8lcklEiBWTgUhwBcAI0DtDmbzKsD6R1Cov9PLKFvJC43VuyzAUqpve1_rRfXbttp9HlUhnc3jU9dTVeGAuv7eBELovogIjT0l9iXVVjzjI_UV9sHiwTueqMx7bDOt8VJuUw_GH2HRGhRGgZt6xpWxYbmBAu1wqOJVF-NhzNtryBEbNOpElNX9dduf2-UDUZQBA_180xajNETLwnASLIpgHv4NWsj0shedw1XGewIMG8VO2gSxKuyGwrQ1mMNMF_pdOJsk41z0hYFtx-lnO3DOccIIr7N6XcBmwVqB-of2dnwW6VyHZCmNjPxvbA8_NYVO4nG9eah-ZyuYw1-j2kvkkUvZ3DYQWxKldC1LmF_deXeHDFc1Xk6nqPz4STJvZQSsPButu6uOr0PXI6XokkhgZ3t6A");
        map.add("redirect_uri", redirect_uri);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        accessToken = response.getBody();
        String[] result = accessToken.split(",");
        token = result[4];
        return token;
    }

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


