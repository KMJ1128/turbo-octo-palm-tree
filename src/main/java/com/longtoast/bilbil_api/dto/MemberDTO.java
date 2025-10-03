package com.longtoast.bilbil_api.dto;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
public class MemberDTO {

    private int id;
    private String nickname;
    private String address;
    private double locationLatitude;
    private double locationLongitude;
    //private String email;
    // 필요한 다른 필드들 추가
}