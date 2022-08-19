package com.campfire.smeal.config.oauth;

import com.campfire.smeal.config.auth.PrincipalDetails;
import com.campfire.smeal.model.RoleType;
import com.campfire.smeal.model.User;
import com.campfire.smeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

//    @Autowired(required = true)
//    public PrincipalOauth2UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
//        System.out.println("PrincipalOauth2UserService");
//        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
//        this.userRepository = userRepository;
//    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("getClientRegistration : "+userRequest.getClientRegistration()); // registrationId로 어떤 OAuth로 로그인 했는지 확인 가능
        System.out.println("getAccessToken : "+userRequest.getAccessToken().getTokenValue() );

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
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        } else {
            System.out.println("우리는 구글과 네이버만 지원해요");
        }

        String provider = oAuth2UserInfo.getProvider(); // google
        String providerId = oAuth2UserInfo.getProviderId();
        String username = oAuth2UserInfo.getEmail();
        String password = bCryptPasswordEncoder.encode("passwordEncode");
        String userId = username + "_" + provider + "_" + providerId; //google_id값,
        String email = oAuth2UserInfo.getEmail();
        RoleType role = RoleType.USER;
        String nickname=oAuth2UserInfo.getName();


        // 이미 회원가입이 됐는지 확인
        User userEntity = userRepository.findByUsername(username);

        if (userEntity == null) { // 못찾음
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
                    .build();
            userRepository.save(userEntity);
        } else {
            System.out.println("로그인을 이미 한 적이 있습니다. 당신은 자동회원가입이 되어 있습니다.");
        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());

    }
}
