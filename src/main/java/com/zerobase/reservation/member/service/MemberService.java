package com.zerobase.reservation.member.service;

import com.zerobase.reservation.member.entity.MemberType;
import com.zerobase.reservation.member.request.PartnerShip;
import com.zerobase.reservation.member.request.SignIn;
import com.zerobase.reservation.member.request.SignUp;
import com.zerobase.reservation.member.entity.Member;
import com.zerobase.reservation.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 회원 서비스
 * 회원 가입, 로그인 등 회원 정보와 관련된 서비스를 수행한다.
 */
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * 요청폼의 정보를 이용하여 DB에 저장한다.
     * 패스워드는 암호화한 후 저장한다.
     */
    @Transactional
    public Member signUp(SignUp request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 가입되어있는 email입니다.");
        }
        request.encodingPassword(passwordEncoder);
        return memberRepository.save(Member.from(request));
    }

    /**
     * 로그인
     * 요청폼의 정보를 이용하여 회원 정보를 조회하고 패스워드 일치 여부를 체크한다.
     */
    public Member signIn(SignIn request) {
        Member member = findMemberByEmail(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("아이디 혹은 비밀번호가 일치하지 않습니다.");
        }
        return member;
    }

    /**
     * 파트너 전환
     * 회원의 타입을 파트너로 전환한다.
     */
    @Transactional
    public Member getPartnerShip(PartnerShip request) {
        Member member = memberRepository.findById(request.getId())
                .orElseThrow(() -> new UsernameNotFoundException("유저 정보를 찾을 수 없습니다."));

        if (Objects.equals(member.getMemberType(), MemberType.PARTNER)) {
            throw new RuntimeException("이미 파트너로 등록되어 있습니다.");
        }

        member.setMemberType(MemberType.PARTNER);

        return memberRepository.save(member);
    }

    /**
     * 이메일을 이용하여 회원 정보를 조회한다.
     */
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("유저 정보를 찾을 수 없습니다."));
    }

    /**
     * UserDetailsService 필수 오버라이드이나 이메일을 통해 조회하는 만큼 헷갈릴 여지가 있어 사용하지 않고 있음
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return null;
    }
}
