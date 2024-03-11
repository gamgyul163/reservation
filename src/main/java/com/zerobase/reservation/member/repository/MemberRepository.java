package com.zerobase.reservation.member.repository;

import com.zerobase.reservation.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * 가입된 회원중 중복된 이메일의 소유자가 있는지 확인한다.
     */
    boolean existsByEmail(String email);

    /**
     * 이메일을 이용하여 회원 정보를 찾는다.
     * 로그인 토큰 발급에 사용
     */
    Optional<Member> findByEmail(String email);
}
