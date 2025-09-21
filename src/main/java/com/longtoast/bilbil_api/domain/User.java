package com.longtoast.bilbil_api.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 자동 증가 ID

    @Column(unique = true, nullable = false)
    private String nickname; // 닉네임, 로그인에 사용

    private String email; // optional, 소셜 로그인시 이메일이 있으면 저장

    private String profileImageUrl; // optional, 프로필 이미지

    private Integer creditScore = 720; // 기본 신용점수

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SocialLogin> socialLogins; // 여러 소셜 계정 연결 가능
}
