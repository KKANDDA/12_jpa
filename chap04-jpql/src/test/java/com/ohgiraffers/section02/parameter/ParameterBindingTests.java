package com.ohgiraffers.section02.parameter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Scanner;

public class ParameterBindingTests {

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
    파라미터를 바인딩하는 방법
    1. 이름 기준 파라미터 ':' 다음에 이름 기준 파라미터를 지정한다.
    2. 위치 기준 파라미터 '?' 다음에 값을 주고 위치 값은 1부터 시작한다.
    * */

    @Test
    void 이름_기분_파라미터_바인딩_메뉴_조회_테스트(){
        String menuNameParameter = "한우딸기국밥";

        String jpql = "SELECT m FROM menu_section02 m WHERE m.menuName LIKE :menuNameTest";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setParameter("menuNameTest", menuNameParameter).getResultList();

        Assertions.assertNotNull(menuList);
        for (Menu menu : menuList) {
            System.out.println("menu = " + menu);
        }
    }

    @Test
    void 위치_기준_파라미터_바인딩_메뉴_목록_조회_테스트(){
        String menuNameParameter = "한우딸기국밥";
        int menuPriceParameter = 5000;
        String jpql ="SELECT m FROM menu_section02 m WHERE m.menuName = ?1 OR m.menuName = ?2";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setParameter(1, menuNameParameter)
                        .setParameter(2, menuPriceParameter)
                                .getResultList();

        Assertions.assertNotNull(menuList);
        for (Menu menu : menuList) {
            System.out.println("menu = " + menu);
        }
    }

    // 메뉴 이름 입력시 입력한 값이 포함된 메뉴 조회
    @Test
    void 메뉴이름입력시입력한값이포함된메뉴조회(){
//        Scanner sc = new Scanner(System.in);
//        System.out.println("메뉴 이름의 일부를 입력하시면 해당하는 메뉴를 보여드립니다.");
//        String check = sc.nextLine();

        String check = "마늘";

        String jpql = "SELECT m FROM menu_section02 AS m WHERE m.menuName LIKE :nameCheck";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setParameter("nameCheck", "%"+check+"%").getResultList();

        Assertions.assertNotNull(menuList);
        for (Menu menu : menuList) {
            System.out.println("menu = " + menu);
        }
    }
}
