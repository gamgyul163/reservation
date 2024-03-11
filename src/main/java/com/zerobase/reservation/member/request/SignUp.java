package com.zerobase.reservation.member.request;

import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 회원가입 요청 폼
 * 패스워드는 DB에 암호화된 상태로 저장한다.
 */
@Getter
public class SignUp {
    private String email;
    private String name;
    private String password;
    private String phoneNumber;

    public void encodingPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }
}
