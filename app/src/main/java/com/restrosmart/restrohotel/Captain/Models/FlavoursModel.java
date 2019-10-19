package com.restrosmart.restrohotel.Captain.Models;

import java.util.ArrayList;

public class FlavoursModel {

    private int flavourId;
    private String flavourName, flavourImg;
    private ArrayList<FlavourUnitModel> flavourUnitModelArrayList;

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

    public String getFlavourImg() {
        return flavourImg;
    }

    public void setFlavourImg(String flavourImg) {
        this.flavourImg = flavourImg;
    }

    public ArrayList<FlavourUnitModel> getFlavourUnitModelArrayList() {
        return flavourUnitModelArrayList;
    }

    public void setFlavourUnitModelArrayList(ArrayList<FlavourUnitModel> flavourUnitModelArrayList) {
        this.flavourUnitModelArrayList = flavourUnitModelArrayList;
    }
}
