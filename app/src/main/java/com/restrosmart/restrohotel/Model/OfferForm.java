package com.restrosmart.restrohotel.Model;

/**
 * Created by SHREE on 01/11/2018.
 */

public class OfferForm {
    private  int Offer_Id,status;

    private String Menu_Name;
    private  String Menu_Descrip;
    private  String Menu_Image_Name;
    private  String Offer_Name;
    private  String Offer_From;
    private  String Offer_To;
    private  String Offer_Value;

    public int getOffer_Id() {
        return Offer_Id;
    }

    public void setOffer_Id(int offer_Id) {
        Offer_Id = offer_Id;
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

    public String getOffer_Name() {
        return Offer_Name;
    }

    public void setOffer_Name(String offer_Name) {
        Offer_Name = offer_Name;
    }

    public String getOffer_From() {
        return Offer_From;
    }

    public void setOffer_From(String offer_From) {
        Offer_From = offer_From;
    }

    public String getOffer_To() {
        return Offer_To;
    }

    public void setOffer_To(String offer_To) {
        Offer_To = offer_To;
    }

    public String getOffer_Value() {
        return Offer_Value;
    }

    public void setOffer_Value(String offer_Value) {
        Offer_Value = offer_Value;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
