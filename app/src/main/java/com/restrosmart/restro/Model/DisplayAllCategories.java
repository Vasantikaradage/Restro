package com.restrosmart.restro.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DisplayAllCategories {

    @SerializedName("Category_Id")
    @Expose
    private Integer categoryId;
    @SerializedName("Category_Name")
    @Expose
    private String categoryName;
    @SerializedName("C_Image_Name")
    @Expose
    private String cImageName;
    @SerializedName("Hotel_Id")
    @Expose
    private Integer hotelId;
    @SerializedName("Branch_Id")
    @Expose
    private Integer branchId;
    @SerializedName("Pc_Id")
    @Expose
    private Integer pcId;

    /**
     * No args constructor for use in serialization
     *
     */
    public DisplayAllCategories() {
    }

    /**
     *
     * @param categoryName
     * @param pcId
     * @param branchId
     * @param categoryId
     * @param cImageName
     * @param hotelId
     */
    public DisplayAllCategories(Integer categoryId, String categoryName, String cImageName, Integer hotelId, Integer branchId, Integer pcId) {
        super();
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.cImageName = cImageName;
        this.hotelId = hotelId;
        this.branchId = branchId;
        this.pcId = pcId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCImageName() {
        return cImageName;
    }

    public void setCImageName(String cImageName) {
        this.cImageName = cImageName;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getPcId() {
        return pcId;
    }

    public void setPcId(Integer pcId) {
        this.pcId = pcId;
    }

}