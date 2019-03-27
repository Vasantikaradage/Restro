package com.restrosmart.restro.Model;

public class FoodMenuModel {

    private int menuId;
    private String menuName, menuDesc, menuImage;
    private float menuPrice;

    public FoodMenuModel() {
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public String getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(String menuImage) {
        this.menuImage = menuImage;
    }

    public float getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(float menuPrice) {
        this.menuPrice = menuPrice;
    }
}
