package com.ohgiraffers.section03;

import jakarta.persistence.*;

@Entity(name="section03_menu") // 클래스 이름이 같을 경우를 식별하기 위함..
@Table(name="tbl_menu") // 어느 테이블과 맵핑할 건진를 설정..
public class Menu {

    @Id
    @Column(name="menu_code") // 실제 테이블 컬럼명
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 값을 db에서 설정하도록..
    private int menuCode;

    @Column(name="menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name="category_code")
    private int categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;

    public Menu() {
    }

    public Menu(int menuCode, String menuName, int menuPrice, int categoryCode, String orderableStatus) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.categoryCode = categoryCode;
        this.orderableStatus = orderableStatus;
    }

    public int getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(int menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", categoryCode=" + categoryCode +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}
