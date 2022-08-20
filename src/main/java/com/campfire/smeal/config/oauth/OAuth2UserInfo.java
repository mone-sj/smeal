package com.campfire.smeal.config.oauth;

public interface OAuth2UserInfo {
    String getProviderId(); // OAuth client에서 부여한 id값
    String getProvider(); // client
    String getEmail();
    String getName();
    String getAge();
    String getGender();
}
