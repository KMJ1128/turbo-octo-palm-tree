package com.longtoast.bilbil_api.controller;

import com.longtoast.bilbil_api.dto.MemberDTO;
import com.longtoast.bilbil_api.dto.MsgEntity;
import com.longtoast.bilbil_api.service.MemberService;
import jakarta.servlet.http.HttpServletRequest; // 사용하지 않으므로 제거 가능하지만, 원본 구조 유지를 위해 주석 처리
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping; // 💡 GET -> POST 변경
import org.springframework.web.bind.annotation.RequestBody; // 💡 요청 본문(Body)에서 DTO를 받기 위해 사용
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 클라이언트가 보낸 MemberDTO (ID, 주소, 위치 등)를 받아서 서비스에 전달하고,
     * 서비스에서 처리된 정보를 반환하는 API 엔드포인트입니다.
     * (조회이지만 Body를 사용하기 위해 POST를 사용합니다. *보안 고려 시 GET/토큰 추출이 더 좋음*)
     */
    @PostMapping("/info") // 💡 @GetMapping 대신 POST 사용
    public ResponseEntity<MsgEntity> getMemberInfo(
            // 💡 수정: 쿼리 파라미터 대신 요청 본문에서 MemberDTO 전체를 받습니다.
            @RequestBody MemberDTO memberRequest
    ) throws Exception {

        // 1. 클라이언트가 보낸 MemberDTO 객체를 서비스 메서드에 그대로 전달합니다.
        // 서비스 메서드 시그니처: getCurrentMemberInfo(MemberDTO dto)
        MemberDTO verifiedInfo = memberService.getCurrentMemberInfo(memberRequest);

        // 2. 성공 응답 생성
        return ResponseEntity.ok()
                // 서비스에서 반환된 (필드값이 그대로 복사된) DTO를 응답 본문에 담습니다.
                .body(new MsgEntity("Success", verifiedInfo));
    }
}