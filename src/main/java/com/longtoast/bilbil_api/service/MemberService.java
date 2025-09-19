package com.longtoast.bilbil_api.service;

import com.longtoast.bilbil_api.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service // 스프링 서비스 빈으로 등록
@RequiredArgsConstructor
public class MemberService {

    // (선택 사항: 데이터베이스 접근을 위한 Repository가 필요할 수 있습니다.)
    // private final MemberRepository memberRepository;

    /**
     * 현재 로그인된 사용자의 정보를 가져오는 로직입니다.
     * 실제 구현 시에는 인증 정보(토큰 등)를 사용하여 데이터베이스에서 회원을 조회해야 합니다.
     * @return 조회된 회원 정보
     */
    public MemberDTO getCurrentMemberInfo() {
        // 🚨 TODO: 실제로는 인증 정보를 분석하여 DB에서 해당 ID의 회원 정보를 조회해야 합니다.
        // 현재는 예시 Mock 데이터를 반환합니다.

        System.out.println("DEBUG: MemberService의 getCurrentMemberInfo 실행됨");

        // DB 조회 및 DTO 변환 과정 생략 후 Mock DTO 반환
        return new MemberDTO(
                1L,
                "ToastLover",
                "toast@example.com"
        );
    }
    
    /* // 추가적인 서비스 메서드 예시
    // public void updateMember(MemberDTO dto) { ... }
    */
}