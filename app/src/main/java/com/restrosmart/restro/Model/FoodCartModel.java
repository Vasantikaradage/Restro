package com.restrosmart.restro.Model;

public class FoodCartModel {

    private int menuId, menuQty;
    private String menuName, menuDesc, menuImage;
    private float menuPrice, menuQtyPrice;

    public FoodCartModel() {
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getMenuQty() {
        return menuQty;
    }

    public void setMenuQty(int menuQty) {
        this.menuQty = menuQty;
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

    public float getMenuQtyPrice() {
        return menuQtyPrice;
    }

    public void setMenuQtyPrice(float menuQtyPrice) {
        this.menuQtyPrice = menuQtyPrice;
    }
}
