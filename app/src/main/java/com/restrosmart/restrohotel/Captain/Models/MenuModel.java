package com.restrosmart.restrohotel.Captain.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MenuModel implements Parcelable {
    private String menuName, menuDisp, liqMLQty;
    private int menuPrice, menuQty;
    private ArrayList<ToppingsModel> arrayListToppings;

    protected MenuModel(Parcel in) {
        menuName = in.readString();
        menuDisp = in.readString();
        liqMLQty = in.readString();
        menuPrice = in.readInt();
        menuQty = in.readInt();
        arrayListToppings = in.createTypedArrayList(ToppingsModel.CREATOR);
    }

    public MenuModel() {
    }

    public static final Creator<MenuModel> CREATOR = new Creator<MenuModel>() {
        @Override
        public MenuModel createFromParcel(Parcel in) {
            return new MenuModel(in);
        }

        @Override
        public MenuModel[] newArray(int size) {
            return new MenuModel[size];
        }
    };

    public String getLiqMLQty() {
        return liqMLQty;
    }

    public void setLiqMLQty(String liqMLQty) {
        this.liqMLQty = liqMLQty;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDisp() {
        return menuDisp;
    }

    public void setMenuDisp(String menuDisp) {
        this.menuDisp = menuDisp;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public int getMenuQty() {
        return menuQty;
    }

    public void setMenuQty(int menuQty) {
        this.menuQty = menuQty;
    }

    public ArrayList<ToppingsModel> getArrayListToppings() {
        return arrayListToppings;
    }

    public void setArrayListToppings(ArrayList<ToppingsModel> arrayListToppings) {
        this.arrayListToppings = arrayListToppings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(menuName);
        dest.writeString(menuDisp);
        dest.writeString(liqMLQty);
        dest.writeInt(menuPrice);
        dest.writeInt(menuQty);
        dest.writeTypedList(arrayListToppings);
    }
}
