package com.restrosmart.restrohotel.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SHREE on 17/10/2018.
 */

public class CategoryForm implements Parcelable {
    private  int Category_id;
    private  String Category_Name;
    private  String C_Image_Name;
    private  int Hotel_Id;
    private  int Branch_Id;
    private  int Pc_Id;
    private  String default_image;

    public CategoryForm() {
    }

    public CategoryForm(int category_id, String category_Name, String c_Image_Name, int hotel_Id, int branch_Id, int pc_Id,String default_image) {
        Category_id = category_id;
        Category_Name = category_Name;
        C_Image_Name = c_Image_Name;
        Hotel_Id = hotel_Id;
        Branch_Id = branch_Id;
        Pc_Id = pc_Id;
        default_image=default_image;
    }

    protected CategoryForm(Parcel in) {
        Category_id = in.readInt();
        Category_Name = in.readString();
        C_Image_Name = in.readString();
        Hotel_Id = in.readInt();
        Branch_Id = in.readInt();
        Pc_Id = in.readInt();
        default_image=in.readString();
    }

    public static final Creator<CategoryForm> CREATOR = new Creator<CategoryForm>() {
        @Override
        public CategoryForm createFromParcel(Parcel in) {
            return new CategoryForm(in);
        }

        @Override
        public CategoryForm[] newArray(int size) {
            return new CategoryForm[size];
        }
    };

    public int getCategory_id() {
        return Category_id;
    }

    public void setCategory_id(int category_id) {
        Category_id = category_id;
    }


    public String getCategory_Name() {
        return Category_Name;
    }

    public void setCategory_Name(String category_Name) {
        Category_Name = category_Name;
    }

    public String getC_Image_Name() {
        return C_Image_Name;
    }

    public void setC_Image_Name(String c_Image_Name) {
        C_Image_Name = c_Image_Name;
    }

    public int getHotel_Id() {
        return Hotel_Id;
    }

    public void setHotel_Id(int hotel_Id) {
        Hotel_Id = hotel_Id;
    }

    public int getBranch_Id() {
        return Branch_Id;
    }

    public void setBranch_Id(int branch_Id) {
        Branch_Id = branch_Id;
    }

    public int getPc_Id() {
        return Pc_Id;
    }

    public void setPc_Id(int pc_Id) {
        Pc_Id = pc_Id;
    }

    public String getDefault_image() {
        return default_image;
    }

    public void setDefault_image(String default_image) {
        this.default_image = default_image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Category_id);
        dest.writeString(Category_Name);
        dest.writeString(C_Image_Name);
        dest.writeInt(Hotel_Id);
        dest.writeInt(Branch_Id);
        dest.writeInt(Pc_Id);
        dest.writeString(default_image);
    }

    @Override
    public String toString() {
        return Category_Name;
    }
}
