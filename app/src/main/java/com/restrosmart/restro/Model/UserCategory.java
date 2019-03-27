package com.restrosmart.restro.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserCategory implements Parcelable {

    private int categoryId;
    private String categoryImage, categoryName;

    public UserCategory() {
    }

    protected UserCategory(Parcel in) {
        categoryId = in.readInt();
        categoryImage = in.readString();
        categoryName = in.readString();
    }

    public static final Creator<UserCategory> CREATOR = new Creator<UserCategory>() {
        @Override
        public UserCategory createFromParcel(Parcel in) {
            return new UserCategory(in);
        }

        @Override
        public UserCategory[] newArray(int size) {
            return new UserCategory[size];
        }
    };

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(categoryId);
        parcel.writeString(categoryImage);
        parcel.writeString(categoryName);
    }
}
