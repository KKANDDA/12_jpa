package com.ohgiraffers.chap05springdata.restapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/*
* @Controller와 @ResponseBody 어노테이션을 합친 어노테이션이다.
* 클래스 레벨에 작성하며 해당 클래스 내 모든 핸들러 메소드에 @ResponseBody 어노테이션을 묵시적으로 적용한다.
* */
@RestController
@RequestMapping("/response")
public class ResponseRestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/random")
    public int getRandomNumber(){
        return (int)(Math.random()*100)+1;
    }

    // object 응답
    @GetMapping("/message")
    public Message getMessage(){
        return new Message(200, "성공적인 응답!~~@!@!");
    }

    // List 응답
    @GetMapping("/list")
    public List<Message> getList(){
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(200, "정상응답"));
        messages.add(new Message(404, "페이지 찾을 수 없음"));
        messages.add(new Message(500, "개발자의 잘 못"));

        return messages;
    }
}
