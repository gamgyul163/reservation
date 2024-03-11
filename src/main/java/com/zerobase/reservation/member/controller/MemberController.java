package com.zerobase.reservation.member.controller;

import com.zerobase.reservation.member.request.PartnerShip;
import com.zerobase.reservation.member.request.SignIn;
import com.zerobase.reservation.member.request.SignUp;
import com.zerobase.reservation.member.entity.Member;
import com.zerobase.reservation.member.service.MemberService;
import com.zerobase.reservation.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 회원 가입, 로그인, 점장 등록 요청을 처리한다.
 */
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    /**
     *회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUp request) {
        return ResponseEntity.ok(memberService.signUp(request));
    }

    /**
     * 로그인
     * 조회된 회원정보를 이용하여 jwt를 담아 응답한다.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignIn request) {
        Member member = memberService.signIn(request);
        String token = tokenProvider.generateToken(member.getEmail(), member.getMemberType());
        return ResponseEntity.ok(token);
    }

    /**
     * 파트너(점장) 가입 요청
     */
    @PostMapping("/partnership")
    public ResponseEntity<?> partnerShip(@RequestBody PartnerShip request) {
        return ResponseEntity.ok(memberService.getPartnerShip(request));
    }
}
