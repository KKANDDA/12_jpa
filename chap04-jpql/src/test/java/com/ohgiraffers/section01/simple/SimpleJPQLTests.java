package com.ohgiraffers.section01.simple;

import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import java.util.List;

public class SimpleJPQLTests {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    @BeforeAll
    public static void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }
    @AfterAll
    public static void closeManager() {
        entityManagerFactory.close();
    }

    @AfterEach
    public void closeEntityManager() {
        entityManager.close();
    }

    /*
    jpql (java Persistence Query Language)
    jpql은 엔티티 객체를 중심으로 개발할 수 있는 객체 지향 쿼리이다.
    sql보다 간결하며 특정 dbms에 의존하지 않는다.
    방언을 통해 해당 DBMS에 맞는 sql을 실행하게 된다.
    jpql은 find() 메소드를 통한 조회와 다르게 항상 데이터 베이스에 sql을 실행해서 결과를 조회한다.
    * */

    /*
    jpql의 기본 문법
    SELECT, UPDATE, DELETE 등의 키워드는 SQL과 동일하다.
    INSERT는 PERSIST를 사용하면 된다.
    키워드는 대소문자를 구분하지 않지만, 엔티티와 속성은 대소문자를 구분함에 유의한다.
    별칭을 사용하는 것이 권장된다.
    별칭 없이도 쿼리가 작동할 수 있으나, 붙이는 편을 권장한다.

    jpql 사용방법은 다음과 같다.
    1. 작성한 jpql을 createQuery()를 통해 쿼리 객체로 만든다.
    2. 쿼리 객체는 TypeQuery, Query 두 가지가 있다.
        - TypeQuery: 반환할 타입을 명확하게 지정하는 방식일 때 사용하며 쿼리 객체의 메소드 실행 결과로 지정된 타입이 반환된다.
        - Query: 반환할 타입을 명확하게 지정할 수 없을 때 사용하며 쿼리 객체 메소드의 실생 결과로 Object 또는 Object[]가 반환 된다.

    3. 쿼리 객체에서 제공하는 메소드 getSingleResult() 또는 getResultList()를 호출하여 쿼리를 실행하고 DB를 조회한다.
        - getSingleResult(): 결과가 정확리 한 행일 경우 사용하며 없거나 많으면 에러가 발생
        - getResultList(): 결과가 2행 이상을 경우 사용하며 컬렉션을 반환한다. 결과가 없으면 빈 컬렉션을 반환한다.
    * */

    @Test
    void Query를_이용한_단일메뉴_조회_테스트(){

        String jpql = "SELECT m.menuName FROM menu_section01 AS m WHERE m.menuCode =999";
        Query query = entityManager.createQuery(jpql);

        Object result = query.getSingleResult();
        Assertions.assertTrue(result instanceof String);
        Assertions.assertEquals("수박죽", result);
    }

    @Test
    void Query를_이용한_다중행_조회_테스트(){
        String jpql = "SELECT m.menuName FROM menu_section01 AS m";
        Query query = entityManager.createQuery(jpql);

        List<Object> foundMenuList = query.getResultList();
        Assertions.assertNotNull(foundMenuList);
        for(Object menu : foundMenuList){
            System.out.println("menu = " + menu);
        }
    }

    @Test
    void TypeQuery를_이용한_단일행_조회_테스트(){
        String jpql = "SELECT m FROM menu_section01 AS m WHERE m.menuCode = 999";
        // 전체조회를 원하면 * 대신 별칭을 넣어야 한다. 자기 이름을 넣어도 아니 된다!
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);

        Menu foundMenu = query.getSingleResult();

        Assertions.assertEquals(999, foundMenu.getMenuCode());
        System.out.println("FoundMenu = " + foundMenu);
    }

    @Test
    void TypeQuery를_이용한_다중행_조회_테스트(){
        String jpql = "SELECT m.menuName FROM menu_section01 AS m";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class); // 쿼리를 실행하고 나온 값을 담을 때 타입을 강제, 검증할 수 있다.

        List<Menu> foundMenuList = query.getResultList();
        Assertions.assertNotNull(foundMenuList);
        for(Menu menu : foundMenuList){
            System.out.println("menu = " + menu);
        }
    }

    @Test
    void distinct를_활용한_중복제거_여러행_조회_테스트(){
        String jpql = "SELECT DISTINCT m.categoryCode FROM menu_section01 AS m";
        TypedQuery<Integer> query =entityManager.createQuery(jpql, Integer.class);
        List<Integer> categoryList = query.getResultList();

        Assertions.assertNotNull(categoryList);
        for (int category : categoryList){
            System.out.println("category = " + category);
        }
    }

    // 카테고리 코드가 6번, 10번인 메뉴 조회
    @Test
    void 카테고리코드가6번10번인메뉴조회(){
        String jpql = "SELECT m FROM menu_section01 AS m WHERE m.categoryCode =6 OR m.categoryCode = 10";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        List<Menu> menuList = query.getResultList();
        Assertions.assertNotNull(menuList);
        for(Menu menu : menuList){
            System.out.println("menu = " + menu);
        }
    }

    // 메뉴 이름에 마늘이 포함된 메뉴
    @Test
    void 메뉴이름에마늘이포함된메뉴(){
        String jpql = "SELECT m.menuName FROM menu_section01 AS m WHERE m.menuName LIKE '%마늘%'";
        // TypedQuery<String> query = entityManager.createQuery(jpql, String.class); // 메소드 체이닝으로.. getResultList()로 축약이 가능하다.
        List<String> menuNameList = entityManager.createQuery(jpql, String.class).getResultList();

        Assertions.assertNotNull(menuNameList);
        for(String menuName : menuNameList){
            System.out.println("menuName = " + menuName);
        }
    }

    /*
    Mybatis는 복잡한 SQL 쿼리와 데이터베이스 성능 최적화 측면에서,
    JPA는 객체 지향적인 데이터 접근과 자동화된 상태 관리 측면에서 주로 사용된다.

    JPA는 객체 지향적인 패러다임을 통해 DB와의 연동을 자동화하지만,
    복잡한 SQL 쿼리나 대량의 데이터 처리에서는 성능에 제한이 있다.
    이런 경우 Mybatis는 명시적으로 복잡한 쿼리를 작성하는 데 적합하며, 최적화에 유리하다.

    Mybatis는 복잡한 조인 서브쿼리, 다양한 함수 등을 처리할 때 더 유연하고,
    쿼리의 복잡성을 완전히 개발자가 컨트롤할 수 있다.
    SQL을 직접 작성하여 사용하므로, 객체 변환 없이 순수 SQL을 실행할 수 있다.
    jpa는 orm을 통해 객체로 변환하는 과정을 거쳐야 하고, 복잡해질 수록 성능적 문제와 추가적인 시간이 소요된다.
    * */
}
