package com.zerobase.reservation.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회원 타입 종류
 * 스프링 시큐리티 기본 prefix에 맞추어 value로 ROLE_ 이 붙은 문자열을 지닌다.
 */
@AllArgsConstructor
@Getter
public enum MemberType {
    USER("ROLE_USER"),
    PARTNER("ROLE_PARTNER");

    private final String value;
}
