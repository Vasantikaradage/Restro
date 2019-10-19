package com.restrosmart.restrohotel.Captain.Models;

import java.util.ArrayList;

public class LiquorBrandsModel {

    private int brandId;
    private String brandName;
    private ArrayList<SpecificLiqourBrandModel> arrayList;

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public ArrayList<SpecificLiqourBrandModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<SpecificLiqourBrandModel> arrayList) {
        this.arrayList = arrayList;
    }
}
