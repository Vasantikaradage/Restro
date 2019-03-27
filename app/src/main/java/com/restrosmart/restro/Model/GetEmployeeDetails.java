package com.restrosmart.restro.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetEmployeeDetails implements Parcelable {

    @SerializedName("Emp_Id")
    @Expose
    private Integer empId;
    @SerializedName("Emp_Name")
    @Expose
    private String empName;
    @SerializedName("Emp_Img")
    @Expose
    private String empImg;
    @SerializedName("Emp_Mob")
    @Expose
    private String empMob;
    @SerializedName("Emp_Email")
    @Expose
    private String empEmail;
    @SerializedName("Emp_Address")
    @Expose
    private String empAddress;
    @SerializedName("Emp_Adhar_Id")
    @Expose
    private String empAdharId;
    @SerializedName("User_Name")
    @Expose
    private String userName;
    @SerializedName("Active_Status")
    @Expose
    private Integer activeStatus;
    @SerializedName("Hotel_Name")
    @Expose
    private String hotelName;
    @SerializedName("Role")
    @Expose
    private String role;
    @SerializedName("Branch_Name")
    @Expose
    private String branchName;

    /**
     * No args constructor for use in serialization
     *
     */
    public GetEmployeeDetails() {
    }

    /**
     *
     * @param empEmail
     * @param empName
     * @param empMob
     * @param hotelName
     * @param branchName
     * @param activeStatus
     * @param empId
     * @param role
     * @param userName
     * @param empAdharId
     * @param empAddress
     * @param empImg
     */
    public GetEmployeeDetails(Integer empId, String empName, String empImg, String empMob, String empEmail, String empAddress, String empAdharId, String userName, Integer activeStatus, String hotelName, String role, String branchName) {
        super();
        this.empId = empId;
        this.empName = empName;
        this.empImg = empImg;
        this.empMob = empMob;
        this.empEmail = empEmail;
        this.empAddress = empAddress;
        this.empAdharId = empAdharId;
        this.userName = userName;
        this.activeStatus = activeStatus;
        this.hotelName = hotelName;
        this.role = role;
        this.branchName = branchName;
    }

    protected GetEmployeeDetails(Parcel in) {
        if (in.readByte() == 0) {
            empId = null;
        } else {
            empId = in.readInt();
        }
        empName = in.readString();
        empImg = in.readString();
        empMob = in.readString();
        empEmail = in.readString();
        empAddress = in.readString();
        empAdharId = in.readString();
        userName = in.readString();
        if (in.readByte() == 0) {
            activeStatus = null;
        } else {
            activeStatus = in.readInt();
        }
        hotelName = in.readString();
        role = in.readString();
        branchName = in.readString();
    }

    public static final Creator<GetEmployeeDetails> CREATOR = new Creator<GetEmployeeDetails>() {
        @Override
        public GetEmployeeDetails createFromParcel(Parcel in) {
            return new GetEmployeeDetails(in);
        }

        @Override
        public GetEmployeeDetails[] newArray(int size) {
            return new GetEmployeeDetails[size];
        }
    };

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
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

    public String getEmpMob() {
        return empMob;
    }

    public void setEmpMob(String empMob) {
        this.empMob = empMob;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpAdharId() {
        return empAdharId;
    }

    public void setEmpAdharId(String empAdharId) {
        this.empAdharId = empAdharId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (empId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(empId);
        }
        parcel.writeString(empName);
        parcel.writeString(empImg);
        parcel.writeString(empMob);
        parcel.writeString(empEmail);
        parcel.writeString(empAddress);
        parcel.writeString(empAdharId);
        parcel.writeString(userName);
        if (activeStatus == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(activeStatus);
        }
        parcel.writeString(hotelName);
        parcel.writeString(role);
        parcel.writeString(branchName);
    }
}