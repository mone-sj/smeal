package com.campfire.smeal.config.oauth;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes; //getAttributes()

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
        return (String) kakao_account.get("age_range");
    }

    @Override
    public String getGender() {
        Map kakao_account= (Map) attributes.get("kakao_account");
        return (String) kakao_account.get("gender");
    }


}
