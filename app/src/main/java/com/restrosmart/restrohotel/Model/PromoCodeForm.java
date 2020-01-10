package com.restrosmart.restrohotel.Model;

public class PromoCodeForm {
    private  String offerValue,offerPrice,fromDate,toDate,offerDescription,offerFromTime,offerToTime;
    private  int offerId,offerStatus;

    public int getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(int offerStatus) {
        this.offerStatus = offerStatus;
    }

    public String getOfferValue() {
        return offerValue;
    }

    public void setOfferValue(String offerValue) {
        this.offerValue = offerValue;
    }

    public String getOfferPrice() {
        return offerPrice;
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

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public String getOfferFromTime() {
        return offerFromTime;
    }

    public void setOfferFromTime(String offerFromTime) {
        this.offerFromTime = offerFromTime;
    }

    public String getOfferToTime() {
        return offerToTime;
    }

    public void setOfferToTime(String offerToTime) {
        this.offerToTime = offerToTime;
    }
}
