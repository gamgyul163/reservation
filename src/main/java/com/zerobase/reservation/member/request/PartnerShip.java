package com.zerobase.reservation.member.request;

import lombok.Getter;

/**
 * 회원 타입을 파트너로 전환하기위한 요청 폼
 * 별도의 인증이 요구되지 않았기에 id만 있음
 */
@Getter
public class PartnerShip {
    private long id;
}
