package com.restrosmart.restrohotel.Model;

public class OfferMenuForm {
    private  int Offer_maintain_Id;

    private String Menu_Name;
    private  String menuId;
    private  String Menu_Descrip;
    private  String Menu_Image_Name;
    private  String Menu_Ori_Price;
    private  String Menu_Offer_Price;
    private  int buyGetId;

    public int getBuyGetId() {
        return buyGetId;
    }

    public void setBuyGetId(int buyGetId) {
        this.buyGetId = buyGetId;
    }

    public int getOffer_maintain_Id() {
        return Offer_maintain_Id;
    }

    public void setOffer_maintain_Id(int offer_maintain_Id) {
        Offer_maintain_Id = offer_maintain_Id;
    }

    public String getMenu_Name() {
        return Menu_Name;
    }

    public void setMenu_Name(String menu_Name) {
        Menu_Name = menu_Name;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenu_Descrip() {
        return Menu_Descrip;
    }

    public void setMenu_Descrip(String menu_Descrip) {
        Menu_Descrip = menu_Descrip;
    }

    public String getMenu_Image_Name() {
        return Menu_Image_Name;
    }

    public void setMenu_Image_Name(String menu_Image_Name) {
        Menu_Image_Name = menu_Image_Name;
    }

    public String getMenu_Ori_Price() {
        return Menu_Ori_Price;
    }

    public void setMenu_Ori_Price(String menu_Ori_Price) {
        Menu_Ori_Price = menu_Ori_Price;
    }

    public String getMenu_Offer_Price() {
        return Menu_Offer_Price;
    }

    public void setMenu_Offer_Price(String menu_Offer_Price) {
        Menu_Offer_Price = menu_Offer_Price;
    }
}
