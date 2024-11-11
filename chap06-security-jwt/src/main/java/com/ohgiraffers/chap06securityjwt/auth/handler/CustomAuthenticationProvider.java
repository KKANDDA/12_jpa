package com.ohgiraffers.chap06securityjwt.auth.handler;

import com.ohgiraffers.chap06securityjwt.auth.model.DetailsUser;
import com.ohgiraffers.chap06securityjwt.auth.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Autowired
    private DetailsService detailsService; // 시용자 세부 정보 서비스를 주입

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; // matches를 위한 주입

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 1. 로그인 요청엣 ㅓ전달된 인증 토큰을 가져온다.
        // 사용자가 입력한 아이디와 패스워드가 담겨있는 토큰
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String username = token.getName(); // id
        String password = (String) token.getCredentials();

        // 2. DB에서 username에 해당하는 정보 조회
        DetailsUser foundUser = (DetailsUser)detailsService.loadUserByUsername(username);
        if(!bCryptPasswordEncoder.matches(password, foundUser.getPassword())) {
            throw new BadCredentialsException("password가 일치하지 않습니다.");
        }
        return new UsernamePasswordAuthenticationToken(foundUser, password, foundUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 어던 타입의 인증을 처리할 수 있는지 확인
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
