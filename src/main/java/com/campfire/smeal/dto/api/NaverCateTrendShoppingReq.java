package com.campfire.smeal.dto.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

// 네이버 분야별 트렌드값 요청 DTO - 사용중

@Slf4j
@Getter
@Setter
@Builder
public class NaverCateTrendShoppingReq {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CategoryRequest{
        private String name;
        private List<String> param;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CateTrendRequest {
        private String startDate;
        private String endDate;
        private String timeUnit;

        private List<CategoryRequest> category;
        private String device;
        private String gender;
        private List<String> ages;
    }

    public static CateTrendRequest toCateTrendRequestDto(String searchTrendShopping) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        CateTrendRequest cateTrendRequest =
                mapper.readValue(searchTrendShopping, CateTrendRequest.class);

        log.info("toCateTrendRequestDto.cateTrendRequestDto :");
        System.out.println(cateTrendRequest);

        CateTrendRequest cateTrendRequestToBuild = CateTrendRequest.builder()
                        .startDate(cateTrendRequest.startDate)
                        .endDate(cateTrendRequest.endDate)
                        .timeUnit(cateTrendRequest.timeUnit)
                        .device(cateTrendRequest.getDevice())
                        .gender(cateTrendRequest.getGender())
                        .ages(cateTrendRequest.getAges())
                        .category(cateTrendRequest.getCategory())
                        .build();
        return cateTrendRequestToBuild;
    }

}
