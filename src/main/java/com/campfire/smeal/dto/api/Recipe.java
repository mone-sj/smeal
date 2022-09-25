package com.campfire.smeal.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class Recipe {

    @Getter
    @Setter
    @Slf4j
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Request {
        private String startIdx;
        private String endIdx;
        private String rcp_nm;
        private String rcp_parts_dtls;

        public Request(String rcp_nm, String rcp_parts_dtls) {
            this.startIdx = "1";
            this.endIdx = "10";
            this.rcp_nm = rcp_nm;
            this.rcp_parts_dtls = rcp_parts_dtls;
        }

        public Request(String rcp_parts_dtls) {
            this.startIdx = "1";
            this.endIdx = "100";
            this.rcp_parts_dtls = rcp_parts_dtls;
        }
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Response {

        @Data
        public static class COOKRCP01{
            public String total_count;
            public ArrayList<Row> row;
            @JsonProperty("RESULT")
            public RESULT result;
        }

        @Data
        public static class RESULT{
            @JsonProperty("MSG")
            public String msg;
            @JsonProperty("CODE")
            public String code;
        }

        @Data
        public static class Root{
            @JsonProperty("COOKRCP01")
            public COOKRCP01 cookrcp01;
        }

        @Data
        public static class Row{
            @JsonProperty("RCP_PARTS_DTLS")
            public String rcp_parts_dtls;
            @JsonProperty("RCP_WAY2")
            public String rcp_way2;
            @JsonProperty("MANUAL_IMG20")
            public String manual_img20;
            @JsonProperty("MANUAL20")
            public String manual20;
            @JsonProperty("RCP_SEQ")
            public String rcp_seq;
            @JsonProperty("INFO_NA")
            public String info_na;
            @JsonProperty("INFO_WGT")
            public String info_wgt;
            @JsonProperty("INFO_PRO")
            public String info_pro;
            @JsonProperty("MANUAL_IMG13")
            public String manual_img13;
            @JsonProperty("MANUAL_IMG14")
            public String manual_img14;
            @JsonProperty("MANUAL_IMG15")
            public String manual_img15;
            @JsonProperty("MANUAL_IMG16")
            public String manual_img16;
            @JsonProperty("MANUAL_IMG10")
            public String manual_img10;
            @JsonProperty("MANUAL_IMG11")
            public String manual_img11;
            @JsonProperty("MANUAL_IMG12")
            public String manual_img12;
            @JsonProperty("MANUAL_IMG17")
            public String manual_img17;
            @JsonProperty("MANUAL_IMG18")
            public String manual_img18;
            @JsonProperty("MANUAL_IMG19")
            public String manual_img19;
            @JsonProperty("INFO_FAT")
            public String info_fat;
            @JsonProperty("HASH_TAG")
            public String hash_tag;
            @JsonProperty("MANUAL_IMG02")
            public String manual_img02;
            @JsonProperty("MANUAL_IMG03")
            public String manual_img03;
            @JsonProperty("RCP_PAT2")
            public String rcp_pat2;
            @JsonProperty("MANUAL_IMG04")
            public String manual_img04;
            @JsonProperty("MANUAL_IMG05")
            public String manual_img05;
            @JsonProperty("MANUAL_IMG01")
            public String manual_img01;
            @JsonProperty("MANUAL01")
            public String manual01;
            @JsonProperty("ATT_FILE_NO_MK")
            public String att_file_no_mk;
            @JsonProperty("MANUAL_IMG06")
            public String manual_img06;
            @JsonProperty("MANUAL_IMG07")
            public String manual_img07;
            @JsonProperty("MANUAL_IMG08")
            public String manual_img08;
            @JsonProperty("MANUAL_IMG09")
            public String manual_img09;
            @JsonProperty("MANUAL08")
            public String manual08;
            @JsonProperty("MANUAL09")
            public String manual09;
            @JsonProperty("MANUAL06")
            public String manual06;
            @JsonProperty("MANUAL07")
            public String manual07;
            @JsonProperty("MANUAL04")
            public String manual04;
            @JsonProperty("MANUAL05")
            public String manual05;
            @JsonProperty("MANUAL02")
            public String manual02;
            @JsonProperty("MANUAL03")
            public String manual03;
            @JsonProperty("ATT_FILE_NO_MAIN")
            public String att_file_no_main;
            @JsonProperty("MANUAL11")
            public String manual11;
            @JsonProperty("MANUAL12")
            public String manual12;
            @JsonProperty("MANUAL10")
            public String manual10;
            @JsonProperty("INFO_CAR")
            public String info_car;
            @JsonProperty("MANUAL19")
            public String manual19;
            @JsonProperty("INFO_ENG")
            public String info_eng;
            @JsonProperty("MANUAL17")
            public String manual17;
            @JsonProperty("MANUAL18")
            public String manual18;
            @JsonProperty("RCP_NM")
            public String rcp_nm;
            @JsonProperty("MANUAL15")
            public String manual15;
            @JsonProperty("MANUAL16")
            public String manual16;
            @JsonProperty("MANUAL13")
            public String manual13;
            @JsonProperty("MANUAL14")
            public String manual14;
        }


    }


    @ToString
    @Data
    @Builder
    public static class res_item {
        public String title;
        public String link;
        public String description;
        public String bloggername;
        public String bloggerlink;
        public String postdate;
    }

    @ToString
    @Data
    @Builder
    public static class res {
        private String rcp_nm;
        private String rcp_way2;
        private String att_file_no_main;
        private List<res_item> res_items;
    }

    @Data
    @Builder
    public static class RespFood {
        private String rcp_nm;
        private String rcp_way2;
        private String att_file_no_main;
    }

    public static Comparator<Recipe.RespFood> foodNameComparator=
            new Comparator<RespFood>() {
                @Override
                public int compare(RespFood o1, RespFood o2) {
                    String foodName1 = o1.getRcp_nm().toUpperCase();
                    String foodName2 = o2.getRcp_nm().toUpperCase();

                    //return 0;
                    return foodName1.compareTo(foodName2);
                }
            };

}
