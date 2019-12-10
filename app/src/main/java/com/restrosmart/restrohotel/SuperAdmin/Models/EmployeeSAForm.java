package com.restrosmart.restrohotel.SuperAdmin.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class EmployeeSAForm implements Parcelable {

    private  String empName,empImg,empEmail,empMob,empAddress,userName,hotelName,Password,empAdharId,Role;
    private  int empId,Role_Id,activeStatus,hotelId;

    public EmployeeSAForm() {
    }



    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpImg() {
        return empImg;
    }

    public void setEmpImg(String empImg) {
        this.empImg = empImg;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpMob() {
        return empMob;
    }

    public void setEmpMob(String empMob) {
        this.empMob = empMob;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmpAdharId() {
        return empAdharId;
    }

    public void setEmpAdharId(String empAdharId) {
        this.empAdharId = empAdharId;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getRole_Id() {
        return Role_Id;
    }

    public void setRole_Id(int role_Id) {
        Role_Id = role_Id;
    }

    public int getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(int activeStatus) {
        this.activeStatus = activeStatus;
    }

    public static Creator<EmployeeSAForm> getCREATOR() {
        return CREATOR;
    }

    protected EmployeeSAForm(Parcel in) {
        empName = in.readString();
        empImg = in.readString();
        empEmail = in.readString();
        empMob = in.readString();
        empAddress = in.readString();
        userName = in.readString();
        hotelName = in.readString();
        Password = in.readString();
        empAdharId = in.readString();
        Role = in.readString();
        empId = in.readInt();
        Role_Id = in.readInt();
        hotelId=in.readInt();
        hotelName=in.readString();
        activeStatus = in.readInt();
    }

    public static final Creator<EmployeeSAForm> CREATOR = new Creator<EmployeeSAForm>() {
        @Override
        public EmployeeSAForm createFromParcel(Parcel in) {
            return new EmployeeSAForm(in);
        }

        @Override
        public EmployeeSAForm[] newArray(int size) {
            return new EmployeeSAForm[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(empName);
        parcel.writeString(empImg);
        parcel.writeString(empEmail);
        parcel.writeString(empMob);
        parcel.writeString(empAddress);
        parcel.writeString(userName);
        parcel.writeString(hotelName);
        parcel.writeString(Password);
        parcel.writeString(empAdharId);
        parcel.writeString(Role);
        parcel.writeInt(empId);
        parcel.writeInt(Role_Id);
        parcel.writeInt(activeStatus);
        parcel.writeString(hotelName);
        parcel.writeInt(hotelId);
    }
}
