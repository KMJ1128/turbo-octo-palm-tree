package com.longtoast.bilbil_api;


import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    // 회원 정보를 임시로 저장할 리스트. 서버를 끄면 데이터가 사라집니다.
    private List<Member> members = new ArrayList<>();
    private long nextId = 1;

    // 회원가입 API
    @PostMapping("/register")
    public Member registerMember(@RequestBody Member member) {
        // 임시 ID 부여
        member.setId(nextId++);
        // 리스트에 회원 정보 추가
        members.add(member);
        // 서버 콘솔에 로그 출력
        System.out.println("회원가입 요청: " + member.getUsername());
        // 추가된 회원 정보를 클라이언트로 반환
        return member;
    }

    // 모든 회원 목록을 반환하는 API
    @GetMapping
    public List<Member> getAllMembers() {
        // 서버 콘솔에 로그 출력
        System.out.println("회원 목록 조회 요청. 총 " + members.size() + "명");
        // 저장된 모든 회원 목록을 클라이언트로 반환
        return members;
    }
}