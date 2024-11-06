package com.ohgiraffers.section05.access.subsection02;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class propertyAccessTests {

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
    void 프로퍼티_접근_테스트(){

        Member member = new Member();
        member.setMemberNo(1);
        member.setMemberId("diobobo");
        member.setMemberPwd("pass01");
        member.setNickname("KKANDDA");

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(member);
        transaction.commit();

        String jpql = "SELECT memberId FROM member_section05_subsection02 WHERE memberNo = 1";
        String registedId = entityManager.createQuery(jpql, String.class).getSingleResult();
        System.out.println("registedId = " + registedId);
        Assertions.assertEquals("diobobo", registedId);

        // entity매니저에 두 번 접근해서 두 번씩 출력됐다.
    }

}
