package com.ohgiraffers.section05.groupfunction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

public class GroupFunctionTests {

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
    JPQL의 그룹함수는 COUNT, MAX, MIN, SUM, AVG로 SQL의 그룹함수와 별반 차이가 없다.
    단, 몇 가지 주의사항이 있다.
    1. 그룹함수의 반환 타입은 결과 값이 정수면 Long, 실수면 Double로 반환된다.
    2. 값이 없는 상태에서 count를 제외한 그룹 함수는 null이 되고 count만 0이 된다.
        따라서 반환 값을 담기 취해 선언하는 변수 타입을 기본자료혀ㅑㅇ으로 하게 되면, 조회 결과를 언박싱할 때 NPE가 발생한다. (널포인트잇셉션)
    3. 그룹 함수의 반환 자료형은 Long or Double 형이기 때문에
    Having절에서 그룹 함수 결과 값을 비교하기 위한 파라미터 타입은 Long or Double로 해야한다.

    (기본자료형은 null을 가질 수 없으니.. 랩핑..)
    * */

    @Test
    void 특정_카테고리에_등록된_메뉴수_조회_테스트(){
        int categoryCodeParameter = 4;
        String jpql = "SELECT COUNT(m.menuPrice) FROM menu_section05 m WHERE m.categoryCode = :categoryCode";
        long countOfMenu = entityManager.createQuery(jpql, Long.class)
                .setParameter("categoryCode", categoryCodeParameter)
                .getSingleResult();
        Assertions.assertTrue(countOfMenu>=0);
        System.out.println("countOfMenu = " + countOfMenu);

        // 카운트는 행을 더한다....
    }

    @Test
    void count_제외_조회결과_없을시_테스트(){
        int categoryCodeParameter = 2;

        String jpql = "SELECT SUM(m.menuPrice) FROM menu_section05 m WHERE m.categoryCode = :categoryCode";
        Long sumOfPrice = entityManager.createQuery(jpql, Long.class) // Long으로 해야 null도 들어간다.
                .setParameter("categoryCode", categoryCodeParameter)
                .getSingleResult();

        System.out.println("sumOfPrice = " + sumOfPrice);

        // 썸은 정수 실수를 더한다..
    }

    @Test
    void groupby절과_having절을_사용한_조회_테스트(){
        // having 절에서의 파라미터는 long을 사용한다.
        // 그룹함수의 반환 타입이 Long이므로..
        long minPrice = 50000L;

        String jpql = "SELECT m.categoryCode, SUM(m.menuPrice) FROM menu_section05 m " +
                "GROUP BY m.categoryCode HAVING SUM(m.menuPrice) >= :minPrice";
        // 프럼 보고 웨얼을 보고 그룹바이 보고 그룹바이의 조건 해빙을 본다.
        // 그러니까.. 메뉴 섹션5 테이블에서 메뉴를 카테고리 코드별로 묶고 그렇게 코드별로 묶인 메뉴들의 가격들을 더하고,
        // 그 더한 값이 민프라이스보다 같거나 큰것들 중에서..
        // 카테고리 코드와, 합쳐친 가격을 모두 뽑아 온다.

        List<Object[]>  sumPriceOfCategoryList = entityManager.createQuery(jpql, Object[].class)
                .setParameter("minPrice", minPrice)
                .getResultList();

        Assertions.assertNotNull(sumPriceOfCategoryList);
        for (Object[] objects : sumPriceOfCategoryList) {

            for (Object object : objects) {
                System.out.println("object = " + object);
            }
        }
    }
}
