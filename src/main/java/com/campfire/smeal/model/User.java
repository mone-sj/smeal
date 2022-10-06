package com.campfire.smeal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 로그인시 아이디, 소셜 로그인시 메일주소를 username
    @Column(nullable = false, length = 50)
    private String username;

    // oauth client에게서 받는 이름
    @Column(nullable = false, length = 100)
    private String nickname;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String email;

    private String age;
    private String gender;
    private String foodMbti;
    private String provider;

    private String passwordRepeat;

    // oauth2 로그인시 client에서 사용자에게 부여한 id
    private String providerId;

    // auth2 로그인시 동일회원인지 확인용 (email+provider+providerId)
    @Column(length = 100, unique = true)
    private String userId;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @CreationTimestamp
    private Timestamp createDate;

    public String getCreateDate() {
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String createDateFormat = dateFormat.format(createDate);

        return createDateFormat;
    }

}