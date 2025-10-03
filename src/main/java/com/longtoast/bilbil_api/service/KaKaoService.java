package com.longtoast.bilbil_api.service;

import com.longtoast.bilbil_api.domain.SocialLogin;
import com.longtoast.bilbil_api.domain.User;
import com.longtoast.bilbil_api.dto.KakaoDTO;
import com.longtoast.bilbil_api.dto.MemberTokenResponse;
import com.longtoast.bilbil_api.repository.SocialLoginRepository;
import com.longtoast.bilbil_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import org.slf4j.Logger; // 🚨 Logger import 추가
import org.slf4j.LoggerFactory; // 🚨 LoggerFactory import 추가

@Service
@RequiredArgsConstructor
public class KaKaoService {

    // 🚨 Logger 객체 선언 (Spring에서 로그를 출력하는 표준 방식)
    private static final Logger log = LoggerFactory.getLogger(KaKaoService.class);


    // WebClient.Builder 주입 (Application class에 Bean 등록 필요)
    private final WebClient.Builder webClientBuilder;
    private final UserRepository userRepository;
    private final SocialLoginRepository socialLoginRepository; // social_id 관리
    // application.properties의 kakao.rest-api-key 값을 주입받음
    @Value("${kakao.rest-api-key}")
    private String kakaoRestApiKey;

    /**
     * [주력 로그인 로직] 안드로이드 앱에서 전달받은 카카오 Access Token을 처리합니다.
     * 이 메서드는 KakaoController에서 호출됩니다.
     * * @param kakaoAccessToken 앱에서 받은 카카오 Access Token
     * @return 우리 서비스의 인증 토큰 및 사용자 정보
     */
    public MemberTokenResponse loginWithToken(String kakaoAccessToken) throws Exception {

        // 1. 카카오 API 호출: 토큰을 사용하여 사용자 정보(ID, 이메일, 닉네임)를 가져옵니다.
        KakaoDTO kakaoInfo = getUserInfoFromKakao(kakaoAccessToken);


        // 🚨 사용자 정보를 서버 로그 창에 출력합니다. 🚨
        log.info("--- 카카오 로그인 사용자 정보 ---");
        log.info("카카오 로그인 사용자 정보: ID={}, 닉네임={}", kakaoInfo.getId(), kakaoInfo.getNickname());
        log.info("-----------------------------");

        // 2. 우리 서비스 회원 DB 처리:
        // 🚨 TODO: kakaoInfo.getId()를 사용하여 DB에서 회원 조회 및 저장/업데이트 로직 구현

        // --- DB 처리 시작 ---
        // social_id = "kakao_123456" 형식으로 저장
        String socialId = "kakao_" + kakaoInfo.getId();

        // 이미 가입한 사용자 조회
        SocialLogin socialLogin = socialLoginRepository
                .findBySocialId(socialId)
                .orElse(null);

        User user;
        if (socialLogin != null) {
            user = socialLogin.getUser(); // 이미 존재하면 해당 User 가져오기
        } else {
            // 신규 회원 생성
            user = User.builder()
                    .nickname(kakaoInfo.getNickname())
                    .build();
            user = userRepository.save(user);

            // SocialLogin 기록 생성
            socialLoginRepository.save(SocialLogin.builder()
                    .user(user)
                    .provider("kakao")
                    .socialId(socialId)
                    .accessToken(kakaoAccessToken)
                    .build());
        }
        // --- DB 처리 끝 ---

        // 3. 우리 서비스 인증 토큰 발행: (Mock)
        //  TODO: Spring Security와 JWT를 사용하여 서비스 전용 토큰을 생성해야 함
        String serviceToken = user.getId().toString();

        // 4. 결과 반환
        return new MemberTokenResponse(
                serviceToken,
                user.getId(),
                user.getNickname(),
                user.getAddress(),
                user.getLocationLatitude(),
                user.getLocationLongitude()
        );
    }

    /**
     * [내부 Helper 함수] 카카오 Access Token으로 사용자 정보를 조회
     * WebClient를 사용하여 카카오 서버와 HTTP 통신을 수행
     */
    private KakaoDTO getUserInfoFromKakao(String kakaoAccessToken) throws Exception {

        System.out.println("DEBUG: WebClient로 카카오 토큰 검증 및 사용자 정보 조회 중...");

        KakaoDTO kakaoInfo = webClientBuilder.build()
                .get()
                // 카카오 사용자 정보 조회 공식 URL
                .uri("https://kapi.kakao.com/v2/user/me")
                // Header에 Authorization: Bearer [토큰] 형식으로 Access Token을 전달해야 합니다.
                .header("Authorization", "Bearer " + kakaoAccessToken)
                .retrieve()
                // HTTP 응답 코드가 4xx, 5xx일 경우 예외 처리
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(new RuntimeException("Kakao API Error: " + response.statusCode()))
                )
                // 응답 본문(JSON)을 KakaoDTO 클래스로 역직렬화
                .bodyToMono(KakaoDTO.class)
                .block(); // 비동기 Mono<T>를 동기 T로 변환

        if (kakaoInfo == null || kakaoInfo.getId() == 0) {
            throw new Exception("Failed to retrieve user info from Kakao or ID is zero.");
        }

        return kakaoInfo;
    }
}