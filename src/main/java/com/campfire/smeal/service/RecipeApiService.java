package com.campfire.smeal.service;

import com.campfire.smeal.dto.api.RecipeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RecipeApiService {

    private static String key;
    private static String serviceName;

    @Value("${myinfo.recipeApiService.key}")
    public void setKey(String value) {
        key = value;
    }
    @Value("${myinfo.recipeApiService.serviceName}")
    public void setServiceName(String value) {
        serviceName = value;
    }

//    String key = "36f8547fc2094ee1983f";
//    String serviceName="COOKRCP01";

    public String searchRecipe(RecipeRequest recipeRequest) {
        String RCP_PARTS_DTLS = recipeRequest.getRcp_parts_dtls();
        log.info(RCP_PARTS_DTLS);
        // http://openapi.foodsafetykorea.go.kr/api/인증키/서비스명/요청파일타입/요청시작위치/요청종료위치/변수명=값&변수명=값2
        final String url = "http://openapi.foodsafetykorea.go.kr/api/" + key + "/" + serviceName +
                "/json/" + recipeRequest.getStartIdx() + "/" + recipeRequest.getEndIdx() + "/RCP_PARTS_DTLS=" + RCP_PARTS_DTLS;

        System.out.println("request_url: "+url);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);

    }



}
