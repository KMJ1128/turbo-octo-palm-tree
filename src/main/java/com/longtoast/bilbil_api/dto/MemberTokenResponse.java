package com.longtoast.bilbil_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberTokenResponse {
    // 우리 서비스가 발행한 인증 토큰 (예: JWT)
    private String serviceToken;

    // 로그인된 회원의 기본 정보
    private String nickname;
    private String email;
}