package com.campfire.smeal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Column(nullable = false, length = 50, unique = true) //소셜로그인시, 해당 메일주소를 username으로 하고 추후에 수정할 수 있게 한다.
    private String username; // 로그인시 필요한 아이디

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
    private String providerId; // oauth2 로그인시 client에서 사용자에게 부여한 id
    private String userId; // auth2 로그인시 동일회원인지 확인용 (email+provider+providerId)

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @CreationTimestamp
    private Timestamp createDate;

}