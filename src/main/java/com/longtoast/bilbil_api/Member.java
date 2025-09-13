package com.longtoast.bilbil_api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private Long id;
    private String username;
    private String email;

    // Getter 메서드를 직접 추가하여 Lombok 어노테이션이 작동하지 않을 때를 대비합니다.
    public String getUsername() {
        return this.username;
    }

    // Setter 메서드를 직접 추가하여 Lombok 어노테이션이 작동하지 않을 때를 대비합니다.
    public void setId(Long id) {
        this.id = id;
    }
}