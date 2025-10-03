package com.longtoast.bilbil_api.repository;

import com.longtoast.bilbil_api.domain.SocialLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;




public interface SocialLoginRepository extends JpaRepository<SocialLogin, Integer> {

    // socialId로 SocialLogin 찾기
    Optional<SocialLogin> findBySocialId(String socialId);
}
