package com.ohgiraffers.section05.access.subsection01;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class FieldAccessTests {

    private static EntityManagerFactory entityManagerFactory;

    private static EntityManager entityManager;

    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void closeManager() {
        entityManager.close();
    }

    @AfterAll
    public static void closeFactory(){
        entityManagerFactory.close();
    }

    @Test
    void 필드_접근_테스트(){
        Member member = new Member();
        member.setMemberNo(1);
        member.setMemberId("diobobo");
        member.setMemberPwd("pass01");

        entityManager.persist(member); // 값을 가지고 있는 필드에서 직접 가져온다. 그래서 sout에 담은 내용이 나오지 않는다.

        Member foundMember = entityManager.find(Member.class, 1);
        Assertions.assertEquals(member, foundMember);
        System.out.println("foundMember = " + foundMember);

        // 확인 결과 역시 필드 방식은 게터를 안 쓰고 있다.
    }
}
