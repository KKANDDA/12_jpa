package com.ohgiraffers.section02.column;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class ColumnMappingTests {

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
    public void 컴럼에서_사용하는_속성(){
        Member member = new Member();
        member.setMemberNo(1);
        member.setMemberId("diobobo");
        member.setMemberPwd("pass01");
        member.setNickname("KKANDDA");
        member.setPhone("010-2720-9519");
        member.setAddress("서울시 동작구");
        member.setEmail("diobobo@naver.com");

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(member);
        transaction.commit();

        Member foundMember = entityManager.find(Member.class, member.getMemberNo());
        Assertions.assertEquals(member.getMemberNo(), foundMember.getMemberNo());

        /*
        DB를 보면 닉네임은 없다. 왜? @Transient // 테이블 생성 시 무시. 자바에서는 필요한 값이지만 db에 넣지 않을 때 사용한다.

        다시 사용하면 중복된 테이블 이름이 생겼기 때문에 삭제하고 다시 만들어 진다. - 쌓이는 것이 아닌 날라가고 다시 만들어 진다는 점을 기억하자..
        * */
    }
}
