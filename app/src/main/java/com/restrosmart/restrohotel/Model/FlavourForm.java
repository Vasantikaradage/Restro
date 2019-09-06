package com.restrosmart.restrohotel.Model;

import java.util.ArrayList;

/**
 * Created by SHREE on 28/12/2018.
 */

public class FlavourForm {
    private  int flavourId;
    private  String flavourName;
    private String flavourImage;
    private  int flavourPrice;
    private  int flavourStatus;
    private ArrayList<FlavourUnitForm> arrayListUnits;

    public int getFlavourId() {
        return flavourId;
    }

    public void setFlavourId(int flavourId) {
        this.flavourId = flavourId;
    }

    public String getFlavourName() {
        return flavourName;
    }

    public void setFlavourName(String flavourName) {
        this.flavourName = flavourName;
    }

    public String getFlavourImage() {
        return flavourImage;
    }

    public void setFlavourImage(String flavourImage) {
        this.flavourImage = flavourImage;
    }

    public int getFlavourPrice() {
        return flavourPrice;
    }

    public void setFlavourPrice(int flavourPrice) {
        this.flavourPrice = flavourPrice;
    }


    public int getFlavourStatus() {
        return flavourStatus;
    }

    public void setFlavourStatus(int flavourStatus) {
        this.flavourStatus = flavourStatus;
    }

    public ArrayList<FlavourUnitForm> getArrayListUnits() {
        return arrayListUnits;
    }

    public void setArrayListUnits(ArrayList<FlavourUnitForm> arrayListUnits) {
        this.arrayListUnits = arrayListUnits;
    }
}
