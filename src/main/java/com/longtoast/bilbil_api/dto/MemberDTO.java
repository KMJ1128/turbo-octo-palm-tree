package com.longtoast.bilbil_api.dto;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
public class MemberDTO {

    private Long id;
    private String nickname;
    //private String email;
    // 필요한 다른 필드들 추가
}