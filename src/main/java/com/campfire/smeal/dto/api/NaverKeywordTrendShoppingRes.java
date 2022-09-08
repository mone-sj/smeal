package com.campfire.smeal.dto.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// 네이버쇼핑인사이트 키워드별 클릭량추이/(target)성별/연령별 일때의 결과값
// 삭제해도 될듯???

public class NaverKeywordTrendShoppingRes {


    // 클릭량 추이 응답
    @Data
    @Builder
    private static class keywordclickTrendRes{

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

    // 성별,연령별(target) 응답
    @Data
    @Builder
    private static class keywordTrendTargetRes{

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        private static class ResultData {
            private String period;
            private String group;
            private String ratio;
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
    }

/*

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
*/

}
