package com.longtoast.bilbil_api.controller;

import com.longtoast.bilbil_api.dto.MemberDTO; // Member 정보를 담을 DTO 클래스가 필요합니다.
import com.longtoast.bilbil_api.dto.MsgEntity; // MsgEntity는 응답 형식을 위해 재사용합니다.
import com.longtoast.bilbil_api.service.MemberService; // 서비스 계층 의존성
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("member") // 기본 경로를 'member'로 설정
public class MemberController {

    // Member 관련 로직을 처리할 Service 의존성 주입
    private final MemberService memberService;

    /**
     * 현재 로그인된 회원의 정보를 조회하는 API 엔드포인트입니다.
     * (일반적으로 JWT 등의 인증 토큰을 통해 사용자 정보를 추출합니다.)
     *
     * @param request HTTP 요청 객체 (인증 정보 추출용)
     * @return 성공 시, MemberDTO를 포함하는 응답 엔티티
     * @throws Exception 서비스 로직 처리 중 발생할 수 있는 예외
     */
    @GetMapping("/info")
    public ResponseEntity<MsgEntity> getMemberInfo(HttpServletRequest request) throws Exception {
        // 1. 요청에서 사용자 식별 정보 (예: JWT 토큰의 ID)를 추출하는 로직이 필요합니다.
        // 현재는 예시이므로, Service에서 Mock 데이터를 가져온다고 가정합니다.

        // 2. MemberService를 호출하여 회원 정보를 가져옵니다.
        // 실제 코드에서는 토큰에서 추출한 userId를 인자로 넘겨줄 것입니다.
        MemberDTO memberInfo = memberService.getCurrentMemberInfo();

        // 3. 성공 응답 생성
        return ResponseEntity.ok()
                .body(new MsgEntity("Success", memberInfo));
        // HTTP 200 OK 응답을 반환하며, body에 회원 정보(MemberDTO)를 담고 있습니다.
    }

    /*
    // 예시: 회원 정보 수정 (PUT 또는 PATCH 매핑 사용)
    @PatchMapping("/update")
    public ResponseEntity<MsgEntity> updateMember(@RequestBody MemberDTO updateRequest) {
        memberService.updateMember(updateRequest);

        return ResponseEntity.ok()
                .body(new MsgEntity("Success", null));
    }
    */
}