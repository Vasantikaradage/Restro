package com.restrosmart.restrohotel.Model;

import java.util.ArrayList;

public class RushHourForm {
    private  String fromDate,toDate;
    private String fromTime, toTime;
    private  String message,offerName;
    private  int rushHourId,Offer_Type_Id,activeStatus;
    private ArrayList<OfferMenuForm> arrayListMenu;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public int getRushHourId() {
        return rushHourId;
    }

    public void setRushHourId(int rushHourId) {
        this.rushHourId = rushHourId;
    }

    public int getOffer_Type_Id() {
        return Offer_Type_Id;
    }

    public void setOffer_Type_Id(int offer_Type_Id) {
        Offer_Type_Id = offer_Type_Id;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public ArrayList<OfferMenuForm> getArrayListMenu() {
        return arrayListMenu;
    }

    public void setArrayListMenu(ArrayList<OfferMenuForm> arrayListMenu) {
        this.arrayListMenu = arrayListMenu;
    }

    public int getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(int activeStatus) {
        this.activeStatus = activeStatus;
    }
}
