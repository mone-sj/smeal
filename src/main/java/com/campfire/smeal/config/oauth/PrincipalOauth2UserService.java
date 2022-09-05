package com.campfire.smeal.config.oauth;

import com.campfire.smeal.config.BCryptEnc;
import com.campfire.smeal.config.auth.PrincipalDetails;
import com.campfire.smeal.model.RoleType;
import com.campfire.smeal.model.User;
import com.campfire.smeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptEnc bCryptPasswordEncoder;
    private final UserRepository userRepository;

    private static String pwd;

    @Value("${myinfo.login.password}")
    public void setPwd(String value) {
        pwd = value;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("getClientRegistration : "
                +userRequest.getClientRegistration());
        System.out.println("getAccessToken : "
                +userRequest.getAccessToken().getTokenValue() );

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("getAttributes : "+oAuth2User.getAttributes());

        // 회원가입을 강제로 진행해볼예정
        OAuth2UserInfo oAuth2UserInfo=null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            System.out.println("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo(
                    (Map)oAuth2User.getAttributes().get("response"));
        } else {
            System.out.println("잘못된 요청입니다.");
        }

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = oAuth2UserInfo.getEmail();
        String password = bCryptPasswordEncoder.encodePWD()
                .encode(pwd);
        String userId = username + "_" + provider + "_" + providerId;
        String email = oAuth2UserInfo.getEmail();
        RoleType role = RoleType.ROLE_USER;
        String nickname=oAuth2UserInfo.getName();
        String age = oAuth2UserInfo.getAge();
        String gender = oAuth2UserInfo.getGender();


        // 이미 회원가입이 됐는지 확인
        User userEntity = userRepository.findByUserId(userId).orElse(null);

        if (userEntity == null) {
            System.out.println("로그인이 최초입니다.");
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .nickname(nickname)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .userId(userId)
                    .age(age)
                    .gender(gender)
                    .build();
            userRepository.save(userEntity);
        } else {
            System.out.println("이미 회원입니다. 자동 로그인");

        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());

    }
}
