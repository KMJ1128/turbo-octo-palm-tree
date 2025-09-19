package com.longtoast.bilbil_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MsgEntity {
    private String message;
    private Object data; // 응답 본문에 담길 실제 데이터 (KakaoDTO 등)
}