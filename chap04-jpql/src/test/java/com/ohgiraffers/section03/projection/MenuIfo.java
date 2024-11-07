package com.ohgiraffers.section03.projection;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

// 본래 복합키는 기본키(프라이머리)가 여러 개인 경우를 말하지만..
// 여기서는.. 즉 임베디드에서는 아닌 일반 키도 가능하다.. // 단, 세트.. 조합이 유니크해야 한다.

@Embeddable
public class MenuIfo {

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    public MenuIfo() {
    }

    public MenuIfo(String menuName, int menuPrice) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
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

    @Override
    public String toString() {
        return "MenuIfo{" +
                "menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                '}';
    }
}
