package com.campfire.smeal.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 로그인시 아이디
    @Column(nullable = false, length = 50, unique = true) //소셜로그인시, 해당 메일주소를 username (수정불가)
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

    // oauth2 로그인시 client에서 사용자에게 부여한 id
    private String providerId;

    // auth2 로그인시 동일회원인지 확인용 (email+provider+providerId)
    private String userId;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @CreationTimestamp
    private Timestamp createDate;

}