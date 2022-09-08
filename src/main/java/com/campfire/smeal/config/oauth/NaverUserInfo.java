package com.campfire.smeal.config.oauth;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;


    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes=attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getAge() {
        String age_range = (String) attributes.get("age");
        String age=null;
        if (age_range.indexOf("9")<=2) {
            age = "0";
        } else if (age_range.contains("19")) {
            age = "10";
        } else if (age_range.contains("29")) {
            age = "20";
        } else if (age_range.contains("39")) {
            age = "30";
        } else if (age_range.contains("49")) {
            age = "40";
        } else if (age_range.contains("59")) {
            age = "50";
        } else {
            age = "60";
        }
        return age;
    }

    @Override
    public String getGender() {
        String gender = (String) attributes.get("gender");
        String final_gender = null;
        if (gender.contains("f") || gender.contains("F")) {
            final_gender = "f";
        } else {
            final_gender = "m";
        }
        return final_gender;
    }
}
