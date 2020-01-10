package com.restrosmart.restrohotel.Model;

import java.util.ArrayList;

public class ScratchCardForm {

    private  String fromDate,toDate,discription,fromTime,toTime,offerName;
    private  int offerId,offerTypeId,status,winnerPplCnt,totalPplCnt;
    private ArrayList<OfferMenuForm> offerMenuFormArrayList;


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

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
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

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public int getOfferTypeId() {
        return offerTypeId;
    }

    public void setOfferTypeId(int offerTypeId) {
        this.offerTypeId = offerTypeId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getWinnerPplCnt() {
        return winnerPplCnt;
    }

    public void setWinnerPplCnt(int winnerPplCnt) {
        this.winnerPplCnt = winnerPplCnt;
    }

    public int getTotalPplCnt() {
        return totalPplCnt;
    }

    public void setTotalPplCnt(int totalPplCnt) {
        this.totalPplCnt = totalPplCnt;
    }

    public ArrayList<OfferMenuForm> getOfferMenuFormArrayList() {
        return offerMenuFormArrayList;
    }

    public void setOfferMenuFormArrayList(ArrayList<OfferMenuForm> offerMenuFormArrayList) {
        this.offerMenuFormArrayList = offerMenuFormArrayList;
    }
}
