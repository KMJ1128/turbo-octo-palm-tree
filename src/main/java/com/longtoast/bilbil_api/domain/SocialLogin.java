package com.longtoast.bilbil_api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="social_logins")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;     // kakao, naver 등
    private String socialId;     // 소셜 고유 ID
    private String accessToken;  // 🔑 이 필드가 꼭 있어야 Builder에서 accessToken() 호출 가능

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
