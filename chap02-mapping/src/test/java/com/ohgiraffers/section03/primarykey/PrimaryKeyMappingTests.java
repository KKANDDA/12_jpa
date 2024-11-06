package com.ohgiraffers.section03.primarykey;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

public class PrimaryKeyMappingTests {

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

    /*
    * Primary Key에는 @Id 어노테이션과 @GeneratedValue 어노테이션을 사용한다.
    * @Id 어노테이션은 엔티티 클래스에서 Primary key 역할을 하는 필드를 지정할 때 사용한다.
    * 데이터 베이스마다 기본 키를 생성하는 방식이 서로 다르다.
    * @GeneratedValue는 다음과 같은 속성을 가지고 있다.
    *
    * - stategy: 자동 생성 전략을 지정
    * - GenerationType.IDENTITY: 기본 키 생성을 데이터 베이스에 위임(Mysql의 AutoIncrement)
    * - GenerationType.SEQUENCE: 데이터 베이스 시퀸스 객체 사용(ORACLE의 SEQUENCE)             DB 차이
    * - GenerationType.TABLE: 키 생성 테이블 사용 // 성능이 떨어진다.
    * - GenerationType.AUTO: 자동 선택
    * 이 4개 중 선택해서 사용한다.
    * */

    @Test
    public void 식별자_매핑_테스트(){

        Member member = new Member();

        member.setMemberId("diobobo");
        member.setMemberPwd("pass01");
        member.setNickname("KKANDDA");
        member.setPhone("010-2720-9519");

        Member member2 = new Member();
        member2.setMemberId("diobobo88");
        member2.setMemberPwd("pass02");
        member2.setNickname("삼열");
        member2.setPhone("010-2720-9519");

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(member);
        entityManager.persist(member2); // 한 번에 두 개는 안 된다. 따로하던지 리스트에 넣도 반복문을 돌리던지..
        transaction.commit();

        String jpql = "SELECT A.memberNo FROM member_section03 A"; // db가 바뀌어도 영향을 받지 않는 jpql 쿼리문..
        List<Integer> memberNoList = entityManager.createQuery(jpql, Integer.class).getResultList();
                                                                    //  쿼리 결과가 인트니.. // 리스트로..
        System.out.println("memberNoList = " + memberNoList);
    }
}
