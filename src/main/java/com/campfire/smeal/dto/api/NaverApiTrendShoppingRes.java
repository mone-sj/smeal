package com.campfire.smeal.dto.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;

// API를 통해서 단순히 데이터 받아오는 값 - 클릭량 트렌드 결과
public class NaverApiTrendShoppingRes {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class ResultData {
        private String period;
        private String ratio;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class Results{
        private String title;
        private List<String> category;
        private List<ResultData> data;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ApiCateTrendResDto {
        private String startDate;
        private String endDate;
        private String timeUnit;
        private List<Results> results;

    }


    public static ApiCateTrendResDto toNaverApiCateTrendResDto(String response)
            throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ApiCateTrendResDto naverResponse =
                mapper.readValue(response, ApiCateTrendResDto.class);

        ApiCateTrendResDto naverResponseToBuild = naverResponse.builder()
                .startDate(naverResponse.startDate)
                .endDate(naverResponse.endDate)
                .timeUnit(naverResponse.timeUnit)
                .results(naverResponse.getResults())
                .build();

        return naverResponseToBuild;
        }

}
