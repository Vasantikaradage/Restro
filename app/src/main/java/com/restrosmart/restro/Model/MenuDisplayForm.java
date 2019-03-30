package com.restrosmart.restro.Model;

/**
 * Created by SHREE on 29/10/2018.
 */

public class MenuDisplayForm {

    private String Menu_Name,Menu_Descrip,Menu_Image_Name;

    private  int Menu_Id,Category_Id,Menu_Test,Non_Ac_Rate,Hotel_Id,Branch_Id,status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMenu_Name() {
        return Menu_Name;
    }

    public void setMenu_Name(String menu_Name) {
        Menu_Name = menu_Name;
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

    public int getMenu_Id() {
        return Menu_Id;
    }

    public void setMenu_Id(int menu_Id) {
        Menu_Id = menu_Id;
    }

    public int getCategory_Id() {
        return Category_Id;
    }

    public void setCategory_Id(int category_Id) {
        Category_Id = category_Id;
    }

    public int getMenu_Test() {
        return Menu_Test;
    }

    public void setMenu_Test(int ac_Rate) {
        Menu_Test = ac_Rate;
    }

    public int getNon_Ac_Rate() {
        return Non_Ac_Rate;
    }

    public void setNon_Ac_Rate(int non_Ac_Rate) {
        Non_Ac_Rate = non_Ac_Rate;
    }

    public int getHotel_Id() {
        return Hotel_Id;
    }

    public void setHotel_Id(int hotel_Id) {
        Hotel_Id = hotel_Id;
    }

    public int getBranch_Id() {
        return Branch_Id;
    }

    public void setBranch_Id(int branch_Id) {
        Branch_Id = branch_Id;
    }

    @Override
    public String toString() {
        return Menu_Name;
    }
}
