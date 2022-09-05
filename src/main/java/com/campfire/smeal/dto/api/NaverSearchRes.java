package com.campfire.smeal.dto.api;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@Builder
@ToString
public class NaverSearchRes {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item{
        public String title;
        public String link;
        public String description;
        public String bloggername;
        public String bloggerlink;
        public String postdate;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Root{
        public String lastBuildDate;
        public int total;
        public int start;
        public int display;
        public ArrayList<Item> items;
    }


    public static class img {
        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Item{
            public String title;
            public String link;
            public String thumbnail;
            public String sizeheight;
            public String sizewidth;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Root{
            public String lastBuildDate;
            public int total;
            public int start;
            public int display;
            public ArrayList<Item> items;
        }

    }


}
