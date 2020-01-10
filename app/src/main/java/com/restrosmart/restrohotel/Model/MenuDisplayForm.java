package com.restrosmart.restrohotel.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by SHREE on 29/10/2018.
 */

public class MenuDisplayForm implements Parcelable {

    private String Menu_Name,Menu_Descrip,Menu_Image_Name,Menu_Id,offeerPrice,error,categoryName;

    private  int Category_Id,Menu_Test,Non_Ac_Rate,Hotel_Id,Branch_Id,status,pcId;
    private ArrayList<ToppingsForm> arrayListtoppings;

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public MenuDisplayForm() {
    }

    public int getPcId() {
        return pcId;
    }

    public void setPcId(int pcId) {
        this.pcId = pcId;
    }

    public String getOffeerPrice() {
        return offeerPrice;
    }

    public void setOffeerPrice(String offeerPrice) {
        this.offeerPrice = offeerPrice;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    protected MenuDisplayForm(Parcel in) {
        Menu_Name = in.readString();
        Menu_Descrip = in.readString();
        Menu_Image_Name = in.readString();
        Menu_Id = in.readString();
        Category_Id = in.readInt();
        Menu_Test = in.readInt();
        Non_Ac_Rate = in.readInt();
        Hotel_Id = in.readInt();
        Branch_Id = in.readInt();
        status = in.readInt();
        pcId=in.readInt();
        offeerPrice=in.readString();
        error=in.readString();
        categoryName=in.readString();
        arrayListtoppings = in.createTypedArrayList(ToppingsForm.CREATOR);
    }

    public static final Creator<MenuDisplayForm> CREATOR = new Creator<MenuDisplayForm>() {
        @Override
        public MenuDisplayForm createFromParcel(Parcel in) {
            return new MenuDisplayForm(in);
        }

        @Override
        public MenuDisplayForm[] newArray(int size) {
            return new MenuDisplayForm[size];
        }
    };

    public ArrayList<ToppingsForm> getArrayListtoppings() {
        return arrayListtoppings;
    }

    public void setArrayListtoppings(ArrayList<ToppingsForm> arrayListtoppings) {
        this.arrayListtoppings = arrayListtoppings;
    }

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

    public String getMenu_Id() {
        return Menu_Id;
    }

    public void setMenu_Id(String menu_Id) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Menu_Name);
        dest.writeString(Menu_Descrip);
        dest.writeString(Menu_Image_Name);
        dest.writeString(Menu_Id);
        dest.writeInt(Category_Id);
        dest.writeInt(Menu_Test);
        dest.writeInt(Non_Ac_Rate);
        dest.writeInt(Hotel_Id);
        dest.writeInt(Branch_Id);
        dest.writeInt(status);
        dest.writeInt(pcId);
        dest.writeTypedList(arrayListtoppings);
        dest.writeString(offeerPrice);
        dest.writeString(error);
        dest.writeString(categoryName);
    }
}
