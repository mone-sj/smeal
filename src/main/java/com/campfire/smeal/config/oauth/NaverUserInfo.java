package com.campfire.smeal.config.oauth;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;


    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes=attributes;
    }

    @Override
    public String getProviderId() {
        return null;
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
        return null;
    }

    @Override
    public String getGender() {
        return null;
    }
}
