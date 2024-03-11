package com.zerobase.reservation.member.request;

import lombok.Getter;

/**
 * 로그인 요청 폼
 * jwt 발급에 사용
 */
@Getter
public class SignIn {
    private String email;
    private String password;
}
