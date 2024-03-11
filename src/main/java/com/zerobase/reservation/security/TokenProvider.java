package com.zerobase.reservation.security;

import com.zerobase.reservation.member.entity.MemberType;
import com.zerobase.reservation.member.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * jwt 발급, 유효성 검증 수행
 */
@Component
@RequiredArgsConstructor
public class TokenProvider {
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1시간
    private static final String ROLES_KEY = "role";

    @Value("{spring.jwt.secret}")
    private String secretKey;
    private final MemberService memberService;

    /**
     * jwt 발급
     */
    public String generateToken(String email, MemberType memberType) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put(ROLES_KEY, memberType.getValue());

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * 인증 토큰 생성
     * SecurityContextHolder에 저장할 인증 토큰을 생성한다.
     */
    public Authentication getAuthentication(String email) {
        UserDetails userDetails = this.memberService.findMemberByEmail(getUserEmail(email));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * jwt subject(이메일) 분리
     */
    public String getUserEmail(String token) {
        return this.parseClaims(token).getSubject();
    }

    /**
     * jwt 유효성 검증
     */
    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        Claims claims = this.parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
