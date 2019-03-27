package com.restrosmart.restro.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SHREE on 16/10/2018.
 */

public class TabCategoryModel  implements Parcelable {

    private String productName;
    private String productPrice;
    private String productDesc;
    private String productImageUrl;
    private String productId;
    private String productQty;

    public TabCategoryModel() {
    }

    protected TabCategoryModel(Parcel in) {
        productName = in.readString();
        productPrice = in.readString();
        productDesc = in.readString();
        productImageUrl = in.readString();
    }

    public static final Creator<TabCategoryModel> CREATOR = new Creator<TabCategoryModel>() {
        @Override
        public TabCategoryModel createFromParcel(Parcel in) {
            return new TabCategoryModel(in);
        }

        @Override
        public TabCategoryModel[] newArray(int size) {
            return new TabCategoryModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productName);
        parcel.writeString(productPrice);
        parcel.writeString(productDesc);
        parcel.writeString(productImageUrl);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }
}

