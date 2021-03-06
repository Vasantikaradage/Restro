package com.restrosmart.restrohotel.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public  class MenuForm  implements Parcelable {
    private String menuName,menuDisp,liqMLQty, menuOrderMsg,menuId;
    private  int menuPrice,menuQty,orderDetailId;

    private ArrayList<ToppingsForm> arrayListToppings;

    public MenuForm() {
    }


    protected MenuForm(Parcel in) {
        menuName = in.readString();
        menuDisp = in.readString();
        liqMLQty = in.readString();
        menuOrderMsg = in.readString();
        menuId = in.readString();
        menuPrice = in.readInt();
        menuQty = in.readInt();
        orderDetailId = in.readInt();
        arrayListToppings = in.createTypedArrayList(ToppingsForm.CREATOR);
    }

    public static final Creator<MenuForm> CREATOR = new Creator<MenuForm>() {
        @Override
        public MenuForm createFromParcel(Parcel in) {
            return new MenuForm(in);
        }

        @Override
        public MenuForm[] newArray(int size) {
            return new MenuForm[size];
        }
    };

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }



    public String getMenuOrderMsg() {
        return menuOrderMsg;
    }

    public void setMenuOrderMsg(String menuOrderMsg) {
        this.menuOrderMsg = menuOrderMsg;
    }

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




    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public ArrayList<ToppingsForm> getArrayListToppings() {
        return arrayListToppings;
    }

    public void setArrayListToppings(ArrayList<ToppingsForm> arrayListToppings) {
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
        dest.writeString(menuOrderMsg);
        dest.writeString(menuId);
        dest.writeInt(menuPrice);
        dest.writeInt(menuQty);
        dest.writeInt(orderDetailId);
        dest.writeTypedList(arrayListToppings);
    }
}
