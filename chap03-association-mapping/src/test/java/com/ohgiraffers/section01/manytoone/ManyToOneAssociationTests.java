package com.ohgiraffers.section01.manytoone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class ManyToOneAssociationTests {
    /*
    Association Mapping은 Entity 클래스 간의 관계를 매핑하는 것을 의미한다.
    이를 통해 객체를 이용해 데이터 베이스의 테이블 간의 관계를 매핑할 수 있다..

    다중성에 의한 분류
    연관 관계가 있는 객체 관계에서는 실제로 연관을 가지는 객체의 수에 따라 분류된다.

    - N : 1 연관관계
    - 1 : N 연관관계
    - 1 : 1 연관관계

    Jpa에서 연관관계를 잘 못 설정하면 성능과 데이터 일관성에 큰 영향을 줄 수 있다.
    각 연관관계 유형에 따라 jpa가 데이터 베이스와 상호작용하는 방식이 달라지기 때문에
    잘 못된 매칭 설정은 예상치 못한 쿼리를 발생 시키거나, 잘 못된 접근을 유발할 수 있다.

    테이블 연관 관계는 외래키를 이용하여 양방향 연관 관계의 특징을 가진다. (mysql)
    참조에 의한 객체의 연관 관계는 단방향이다. (jpa)
    객체간의 연관관계를 양방향으로 만들고 싶은 경우 반대 쪽에서도 필드를 추가해 참조를 보관하면 된다.
    하지만 엄밀하게 이는 양방향 관계가 아니라 단방향 관계 2개로 볼 수 있다.
    * */

    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    public static void initFactory() {
        emf = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        em = emf.createEntityManager();
    }

    @AfterAll
    public static void closeFactory() {
        emf.close();
    }

    @AfterEach
    public void closeManager(){
        em.close();
    }

    /*
    ManyToOne은 다수의 엔티티가 하나의 엔티티를 참조하는 상황에서 사용된다.
    예를 들어 하나의 카테고리가 여러 개의 메뉴를 가질 수 있는 상황에서
    메뉴 엔티티가 카테고리를 참조하는 것이다.
    이 때 메뉴 엔티티가 Many, 카테고리 엔티티가 One이 된다.
     */



    @Test
    void 다대일_연관관계_조회_테스트(){
        int menuCode = 15;

        MenuAndCategory foundMenu = em.find(MenuAndCategory.class, menuCode);
        Category menuCategory = foundMenu.getCategory();

        Assertions.assertNotNull(menuCategory);
        System.out.println("menuCategory = " + menuCategory);
    }

    @Test
    void 다대일_연관관계_객체지향쿼리_사용_조회_테스트(){
        String jpql = "SELECT c.categoryName FROM menu_and_category m JOIN m.category c WHERE m.menuCode = 15";
        // 엔티티 대상으로 하는 쿼리문이다.            그래서 name도 엔티티껄 루..

        String category = em.createQuery(jpql, String.class).getSingleResult();

        Assertions.assertNotNull(category);
        System.out.println("category = " + category);
    }

    @Test
    void 다대일_연관관계_객체_삽입_테스트(){

        MenuAndCategory menuAndCategory = new MenuAndCategory();
        menuAndCategory.setMenuCode(9999);
        menuAndCategory.setMenuName("양미리구이");
        menuAndCategory.setMenuPrice(10000);

        // 데이터 베이스에 없는 것을 참조하려 해서 실행되지 않는다. 영속화가 되어있지 않아서.. 존재해야 영속화가 된다..
        Category category = new Category();
        category.setCategoryCode(3333);
        category.setCategoryName("제철음식");
        category.setRefCategoryCode(1);

        menuAndCategory.setCategory(category);
        menuAndCategory.setOrderableStatus("Y");

        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        em.persist(menuAndCategory);
        entityTransaction.commit();

        MenuAndCategory foundMenuAndCategory = em.find(MenuAndCategory.class, 9999);
        Assertions.assertEquals(9999, foundMenuAndCategory.getMenuCode());
        Assertions.assertEquals(3333,foundMenuAndCategory.getCategory().getCategoryCode());
    }

    @Test
    void 영속성_삭제_테스트(){
        MenuAndCategory menuAndCategory = em.find(MenuAndCategory.class, 9999);

        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        em.remove(menuAndCategory);
        entityTransaction.commit();
        MenuAndCategory deleteMenu = em.find(MenuAndCategory.class, 9999);
        Category foundCategory = em.find(Category.class, 3333);
        System.out.println("deleteMenu = " + deleteMenu);
        System.out.println("foundCategory = " + foundCategory);

        // 부모에게 하나의 자식만 있을 때는 함께 지우는 것도 가능하다.
    }

    @Test
    void Merge_Insert_테스트(){

        MenuAndCategory menuAndCategory = new MenuAndCategory();
        menuAndCategory.setMenuCode(15000);
        menuAndCategory.setMenuName("merge insert 메뉴");
        menuAndCategory.setOrderableStatus("Y");
        Category category = new Category();
        category.setCategoryName("merge카테고리");

        menuAndCategory.setCategory(category);

        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        MenuAndCategory mergeMenu = em.merge(menuAndCategory);
        entityTransaction.commit();

        System.out.println("mergeMenu = " + mergeMenu);
    }

    @Test
    void detach_테스트(){
        /*
        Detach의 경우 해당 엔티티를 영속성 컨텍스에서 관리하지 않겠다고 하는 것이다. (준영속화)
        그러나 해당 관계를 맺고 있는 엔티티의 수정이 생기는 경우 해당 엔티티는 관리 중이기 때문에 함께 관계를 가지고 간다.
        이러한 문제를 핼결하기 위해 CascadeType을 DETACH로 설정하면 관계 요소도 함께 영속성에서 관리하지 않겠다는 것이다.
        * */

        MenuAndCategory menuAndCategory = em.find(MenuAndCategory.class, 10000);
        menuAndCategory.setMenuName("양미리조림!!!!!");
        Category category = menuAndCategory.getCategory();
        category.setCategoryName("제철음식");
        menuAndCategory.setCategory(category);

        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        em.detach(menuAndCategory);
        entityTransaction.commit();

        Category category1 = em.find(Category.class, 3334);
        System.out.println("category1 = " + category1);
    }
}
