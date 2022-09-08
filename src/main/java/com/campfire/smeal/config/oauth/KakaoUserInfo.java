package com.campfire.smeal.config.oauth;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {

    //getAttributes() 값
    private Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        String id=null;
        if (attributes.get("id").getClass().getName().equals("java.lang.Long")) {
            id = String.valueOf(attributes.get("id"));
        } else {
            id = (String) attributes.get("id");
        }
        return id;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        Map kakao_account= (Map) attributes.get("kakao_account");
        return (String) kakao_account.get("email");
    }

    @Override
    public String getName() {
        Map properties = (Map) attributes.get("properties");
        return (String) properties.get("nickname");
    }

    @Override
    public String getAge() {
        Map kakao_account= (Map) attributes.get("kakao_account");
        String age_range = (String) kakao_account.get("age_range");
        String age = null;
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
        Map kakao_account= (Map) attributes.get("kakao_account");
        String gender=(String) kakao_account.get("gender");
        String final_gender = null;
        if (gender.contains("f") || gender.contains("F")) {
            final_gender = "f";
        } else {
            final_gender = "m";
        }
        return final_gender;
    }


}
