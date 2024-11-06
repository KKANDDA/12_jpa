package com.ohgiraffers.section06.compositekey.subsection02.idclass;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class IdClassTests {

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
    jpa에서 복합키를 매핑하는 방법에는 두 가지가 있다.
    1, @EmbeddedId 어노테이션
        - 이 방법은 복합 키를 구성하는 필드들을 하나씩 클래스로 묶은 뒤 해당 클래스를 어노테이션을 사용하여 매핑하는 방법이다.
        복합 키의 일부 필드만을 매핑할 수도 있기 때문에, 필드 수가 많은 경우에는 유연한 매핑이 가능하다는 장점이 있다.

    2. @IdClass 어노테이션
        - 이 방법은 복합 키를 구성하는 필드들을 별도의 클래스로 분리한 뒤, 해당 클래스를 @IdClass 어노테이션의 값으로 지정해 주는 것이다.
        이 방법은 별도의 매핑 클래스를 사용하지 않기 때문에 코드가 간결하다. (잘 쓰지 않는 방법..)

    복합 키의 매칭에서는 복합 키 클래스의 equals와 hashCode 메소드를 구현해야 한다는 점에 주의한다.
    이는 jpa에서 엔티티 객체의 동일성을 판단하기 위해 필요하다.
    * */

    @Test
    public void embedded_아이디_사용한_복합키_매핑_테스트() {
        Member member = new Member();

        member.setMemberNo(1);
        member.setMemberEmail("diobobo@naver.com");
        member.setPhone("010-2720-9519");
        member.setAddress("서울시 동작구");

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(member);
        transaction.commit();

        Member foundMember = entityManager.find(Member.class, new MemberPK(1,"diobobo@naver.com"));
        Assertions.assertEquals(member, foundMember);

        // 이퀄스가 제대로 오버라이드 됐다는 뜻..
    }
}
