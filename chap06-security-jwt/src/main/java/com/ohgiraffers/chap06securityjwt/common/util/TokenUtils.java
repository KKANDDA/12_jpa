package com.ohgiraffers.chap06securityjwt.common.util;

// 토큰을 관리할 역할.. 만들고 복호화하고 유효성검사도 한다...

import com.ohgiraffers.chap06securityjwt.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {

    private static String jwtSecretKey;
    private  static long tokenValidateTime;

    @Value("${jwt.key}")
    public void setJwtSecretKey(String jwtSecretKey) {
        TokenUtils.jwtSecretKey = jwtSecretKey;
    }

    @Value("${jwt.time}")
    public void setTokenValidateTime(long tokenValidateTime) {
        TokenUtils.tokenValidateTime = tokenValidateTime;
    }

    /**
     * header의 token을 분리하는 메소드
     * @Param header: Authrization의 header 값을 가져온다.
     * @return token: Authrization의 token을 반환한다. */
    public static String splitHeader(String header){
        if(!header.equals("")){
            return header.split(" ")[1]; // BEARER를 제외한 토큰 값만 반환
        }else {
            return null;
        }
    }



    // 핵심!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    /**
     * 유효한 토큰인지 확인하는 메소드
     * @Param tpken: 토큰
     * @return boolean: 유효 여부 */
    public static boolean isValidToken(String token){

        try{
            Claims claims = getClaimsFromToken(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }




    /**
     * 토큰을 복호화하는 메소드
     * @Param token
     * @return Claims */

    public static Claims getClaimsFromToken(String token){
        // jwts.parser와 parsClaimsJws를 이용해 JWT의 요효성을 검증한다.
        // 만약 JWT가 유요하지 않으면 예외가 발생한다.
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey)). parseClaimsJws(token).getBody();
    }



    // 핵심~!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    /**
     * 토큰을 생성하는 메소드
     * @param user userEntity
     * @return String token */
    public static String genersateJwtToken(User user){
        // 토큰 만료 시간을 현재 시간에서 지정된 유효 시간 이후로 설정
        Date expireTime = new Date(System.currentTimeMillis() + tokenValidateTime);
        // JwtBuilder를 사용해 JWT 토큰을 생성하는 객체 초기화
        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader()) // 토큰의 헤더 설정(헤더에는 토큰의 타입 및 알고리즘 정보가 담김)
                .setClaims(createClaims(user)) // 토큰의 클레임 설정(사용자 정보와 같은 데이터를 담음)
                .setSubject("ohgiraffers token: " + user.getUserNo()) // 토큰의 설명 정보를 담아줌
                .signWith(SignatureAlgorithm.HS256, createSignature()) // 토큰 암호화 방식 정의
                .setExpiration(expireTime); // 만료시간 설정
        // 최종적으로 생성된 토큰을 문자열 형태로 반환
        return builder.compact();
    }




    /**
     * token의 header를 설정하는 메소드
     * @return Map<String, Object> header의 설정 정보 */
    private static Map<String, Object> createHeader(){
        Map<String, Object> header = new HashMap<>();
        // 헤더의 토큰 타임을 JWT로 설정
        header.put("type", "jwt");
        // 헤더의 토큰의 알고리즘을 HS256로 설정
        header.put("alg", "HS256"); // 여기까지는 자동을 만들어준다.. 근데 밑에 부분을 추가하려면 다 추가해 줘야한다.

        // 헤더에 토큰 생성 시간을 밀리초 단위로 추가
        header.put("date", System.currentTimeMillis());

        return header;
    }

    /**
     * 사용자 정보를 기반으로 클레임을 생성해 주는 메소드
     * @param user 사용자 정보
     * @return Map<String, Object> Claims 정보*/
    private static Map<String, Object> createClaims(User user){ // 토큰에 담기는 실제적인 데이터, 비밀번호는 ㄴㄴ
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("role", user.getRole());
        claims.put("userEmail", user.getUserEmail());
        return claims;
    }
    /**
     * Jwt 서명을 발급해 주는 메소드
     * @return key */
    private static Key createSignature(){
        // 비밀 키 문자열을 Base64로 디코딩하여 바이트 배열로 반환
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
        // 변환된 바이트 배열을 알고리즘을 사용해 key 객체로 반환
        // HS256으로 암호화 후 반환함.
        return new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

}
