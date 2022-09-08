package com.campfire.smeal.dto.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// api 응답값 - 쇼핑인사이트 분야별/키워드별 기기별/성별/연령별(target) 응답
public class NaverApiTrendShoppingTargetRes {

    // 분야별 응답
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
    public static class ApiTrendTargetResDto {
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
        private ApiTrendTargetResDto cateTargetTrend;
    }

    public static ApiTrendTargetResDto toNaverApiCateTrendTargetResDto(
            String response)
            throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ApiTrendTargetResDto naverResponse =
                mapper.readValue(response, ApiTrendTargetResDto.class);

        ApiTrendTargetResDto naverResponseToBuild = naverResponse.builder()
                .startDate(naverResponse.startDate)
                .endDate(naverResponse.endDate)
                .timeUnit(naverResponse.timeUnit)
                .results(naverResponse.getResults())
                .build();

        return naverResponseToBuild;
    }

    // 키워드별 응답
    public static class KeywordTargetTrendShoppingRes{

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class ResultData {
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
            private List<String> keyword;
            private List<NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.ResultData> data;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class ApiKeywordTrendTargetResDto {
            private String startDate;
            private String endDate;
            private String timeUnit;
            private List<NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.Results> results;
        }

        // 여기까지 네이버에서 제공하는 응답

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class KeywordTargetRearrRes {
            private String group;
            private String ratio;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class AgeRes{
            public String keyword;
            public ArrayList<KeywordTargetRearrRes> results;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class GenderRes{
            public String keyword;
            public ArrayList<KeywordTargetRearrRes> results;
        }



    }


}
