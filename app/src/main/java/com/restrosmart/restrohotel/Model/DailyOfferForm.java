package com.restrosmart.restrohotel.Model;

import java.util.ArrayList;

public class DailyOfferForm {
    private  String offerName,fromDate,toDate,price,description,offerImg,fromTime,toTime,offerPrice;
    private  int offerId,buyCnt,getCnt,Status,OfferPriceStatus;
    private ArrayList<OfferMenuForm> offerMenuFormArrayList,offerSubMenuArrayList;


    public ArrayList<OfferMenuForm> getOfferSubMenuArrayList() {
        return offerSubMenuArrayList;
    }

    public void setOfferSubMenuArrayList(ArrayList<OfferMenuForm> offerSubMenuArrayList) {
        this.offerSubMenuArrayList = offerSubMenuArrayList;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public int getOfferPriceStatus() {
        return OfferPriceStatus;
    }

    public void setOfferPriceStatus(int offerPriceStatus) {
        OfferPriceStatus = offerPriceStatus;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
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

    public String getOfferImg() {
        return offerImg;
    }

    public void setOfferImg(String offerImg) {
        this.offerImg = offerImg;
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

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public int getBuyCnt() {
        return buyCnt;
    }

    public void setBuyCnt(int buyCnt) {
        this.buyCnt = buyCnt;
    }

    public int getGetCnt() {
        return getCnt;
    }

    public void setGetCnt(int getCnt) {
        this.getCnt = getCnt;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public ArrayList<OfferMenuForm> getOfferMenuFormArrayList() {
        return offerMenuFormArrayList;
    }

    public void setOfferMenuFormArrayList(ArrayList<OfferMenuForm> offerMenuFormArrayList) {
        this.offerMenuFormArrayList = offerMenuFormArrayList;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
