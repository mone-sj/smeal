package com.campfire.smeal.dto.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// API를 통해서 데이터 받아오는 값 - 기기별/성별/연령별 일때의 결과값
public class NaverApiTrendShoppingTargetRes {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class ResultData {
        private String period;
        private String ratio;
        private String group;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Results {
        private String title;
        private List<String> category;
        private List<ResultData> data;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ApiCateTrendTargetResDto {
        private String startDate;
        private String endDate;
        private String timeUnit;

        private List<Results> results;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TotalResults {
        private String category;
        private String period;
        private String ratio;
        private String group;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class NaverTargetTrend {
        private String target;
        private String category;
        private ApiCateTrendTargetResDto cateTargetTrend;
    }

    public static ApiCateTrendTargetResDto toNaverApiCateTrendTargetResDto(
            String response)
            throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ApiCateTrendTargetResDto naverResponse =
                mapper.readValue(response, ApiCateTrendTargetResDto.class);

        ApiCateTrendTargetResDto naverResponseToBuild = naverResponse.builder()
                .startDate(naverResponse.startDate)
                .endDate(naverResponse.endDate)
                .timeUnit(naverResponse.timeUnit)
                .results(naverResponse.getResults())
                .build();

        return naverResponseToBuild;
        }

}
