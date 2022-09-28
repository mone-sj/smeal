package com.campfire.smeal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

}