package com.ohgiraffers.section03.projection;

import jakarta.persistence.*;

@Entity(name = "embedded_menu")
@Table(name = "tbl_menu")
public class EmbeddedMenu {

    @Id
    @Column(name = "menu_code")
    private String menuCode;

    @Embedded
    private MenuIfo menuIfo;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;

    public EmbeddedMenu() {
    }

    public EmbeddedMenu(String menuCode, MenuIfo menuIfo, int categoryCode, String orderableStatus) {
        this.menuCode = menuCode;
        this.menuIfo = menuIfo;
        this.categoryCode = categoryCode;
        this.orderableStatus = orderableStatus;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public MenuIfo getMenuIfo() {
        return menuIfo;
    }

    public void setMenuIfo(MenuIfo menuIfo) {
        this.menuIfo = menuIfo;
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
        return "EmbeddedMenu{" +
                "menuCode='" + menuCode + '\'' +
                ", menuIfo=" + menuIfo +
                ", categoryCode=" + categoryCode +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}