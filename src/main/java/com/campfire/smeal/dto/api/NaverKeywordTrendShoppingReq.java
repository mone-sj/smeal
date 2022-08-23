package com.campfire.smeal.dto.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

// 네이버 키워드별 트렌드값 요청 DTO - 사용중

@Slf4j
@Getter
@Setter
@Builder
public class NaverKeywordTrendShoppingReq {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Keyword{
        private String name;
        private List<String> param;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class KeywordTrendRequest {
        private String startDate;
        private String endDate;
        private String timeUnit;
        private String category;

        private List<Keyword> keyword;
        private String device;
        private String gender;
        private List<String> ages;
    }

    public static KeywordTrendRequest toKeywordTrendRequestDto(
            String request
            ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        KeywordTrendRequest cateTrendRequest =
                mapper.readValue(request, KeywordTrendRequest.class);

        KeywordTrendRequest keywordTrendRequestToBuild = KeywordTrendRequest.builder()
                .startDate(cateTrendRequest.startDate)
                .endDate(cateTrendRequest.endDate)
                .timeUnit(cateTrendRequest.timeUnit)
                .category(cateTrendRequest.getCategory())
                .keyword(cateTrendRequest.getKeyword())
                .device(cateTrendRequest.getDevice())
                .gender(cateTrendRequest.getGender())
                .ages(cateTrendRequest.getAges())
                .build();

        return keywordTrendRequestToBuild;
    }

}
