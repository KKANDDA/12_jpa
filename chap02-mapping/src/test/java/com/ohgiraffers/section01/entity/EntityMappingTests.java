package com.ohgiraffers.section01.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class EntityMappingTests {

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
    void 테이블_만들기_테스트(){
        Member member = new Member();

        member.setMemberNo(1);
        member.setMemberId("diobobo");
        member.setMemberPwd("pass01");
        member.setNickname("KKANDDA");
        member.setPhone("010-2720-9519");

        entityManager.persist(member);

        Member foundMember = entityManager.find(Member.class, member.getMemberNo());
        Assertions.assertEquals(member.getMemberNo(), foundMember.getMemberNo());

        /*Commit하지 않았기 때문에 dml은(CRUD) db에 등록되지 않았지만, ddl 구문은 autoCommit이기 때문에 테이블은 생성되어 있다.
        * 생성되는 컬럼의 순서는 pk가 우선이며, 일반 컴럼은 유니코드 오름차순으로 생성된다.
        *
        * db을 확인하면, 테이블은 만들어졌지만 컬럼들은 추가되지 않았다. */
    }
}
