package com.zerobase.reservation.member.entity;

import com.zerobase.reservation.member.request.SignUp;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

/**
 * 회원 정보 entity, UserDetails를 구현하여 인증, 인가를 수행한다.
 * 점장은 별도 entity로 분리하지 않고 memberType으로 구분하여 role을 통해 제한을 건다.
 */
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String name;
    private String password;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;


    /**
     * 회원가입 요청을 받아 entity로 변환
     */
    public static Member from(SignUp form) {
        return Member.builder()
                .email(form.getEmail())
                .name(form.getName())
                .password(form.getPassword())
                .phoneNumber(form.getPhoneNumber())
                .memberType(MemberType.USER)
                .build();
    }

    /**
     * 회원의 타입을 파트너로 변경한다.
     */
    public void signUpToPartner() {
        this.memberType = MemberType.PARTNER;
    }

    /**
     * 회원의 타입에 따라 role을 리스트 형태로 반환하여 토큰 발급에 사용한다.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(memberType.getValue()));
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
