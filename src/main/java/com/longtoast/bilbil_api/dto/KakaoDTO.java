package com.longtoast.bilbil_api.dto;

import lombok.Data;

@Data // Lombok: Getter, Setter, toString 등을 자동 생성
public class KakaoDTO {

    // 카카오 고유 ID
    private long id;

    // 이메일 (선택 동의 항목)
    private String email;

    // 닉네임
    private String nickname;
}