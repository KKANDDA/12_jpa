package com.ohgiraffers.chap05springdata.restapi;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.*;

@RestController
@RequestMapping("/entity")
public class ResponseEntityTestController {
    /*
    * ReponseEntity 란?
    * 결과 데이터와 http 상태 코드를 직접 제어할 수 있는 클래스이다.
    * httpStatus, HttpHeaders, HttpBody를 포함한다.
    * */

    private List<UserDTO> users;

    public ResponseEntityTestController() {
        users = new ArrayList<>();
        users.add(new UserDTO(1, "user01", "pass01", "홍길동", new Date()));
        users.add(new UserDTO(2, "user02", "pass02", "유관순", new Date()));
        users.add(new UserDTO(3, "user03", "pass03", "이순신", new Date()));
    }

    // 모든 사용자를 조회하는 api 엔드 포인트 메소드
    @GetMapping("/users")
    public ResponseEntity findAllUsers(){
        // http 에더 객체 생성 - 필수 아님
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        // 기본적인 응답이라 생략이 가능..

        // 응답에 포함할 데이터 맵을 생성
        Map<String, Object> response = new HashMap<>();
        response.put("user",users);
        return new ResponseEntity(response, headers, HttpStatus.OK);
    }

    @GetMapping("/users/{userNo}")
    public ResponseEntity findUserByNo(@PathVariable int userNo){

        // http 헤더 객체 생성 - 필수 아님
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        UserDTO foundUser = null;
        for (UserDTO user : users) {
            if(user.getNo() == userNo){
                foundUser = user;
                break;
                // 없는 번호를 조회하면.. null이 담길 것이다.
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("user",foundUser);
        return new ResponseEntity(response, headers, HttpStatus.OK);

    }
}
