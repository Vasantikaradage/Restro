package com.restrosmart.restrohotel.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SHREE on 12/10/2018.
 */

public class UserForm implements Parcelable{
   // @SerializedName("Emp_Mob")
    String Emp_Mob;
   // @SerializedName("Password")
    String Password;
   // @SerializedName("Active_Status")
    String Active_Status;

    /*@SerializedName("Role_Id")
    String Role_Id;*/


  //  @SerializedName("Emp_Id")
    String Emp_Id;


   // @SerializedName("Emp_Name")
    private  String Emp_Name;

  //  @SerializedName("Emp_Img")
    private  String Emp_Img;



  //  @SerializedName("Emp_Email")
    private  String Emp_Email;

  //  @SerializedName("Emp_Address")
    private  String Emp_Address;

   // @SerializedName("Emp_Adhar_Id")
    private  String Emp_Adhar_Id;

   // @SerializedName("User_Name")
    private  String User_Name;

  //  @SerializedName("Role")
    private  String role;

    protected UserForm(Parcel in) {
        Emp_Mob = in.readString();
        Password = in.readString();
        Active_Status = in.readString();
      //  Role_Id = in.readString();
        Emp_Id = in.readString();
        Emp_Name = in.readString();
        Emp_Img = in.readString();
        Emp_Email = in.readString();
        Emp_Address = in.readString();
        Emp_Adhar_Id = in.readString();
        User_Name = in.readString();
        role = in.readString();
    }

    public static final Creator<UserForm> CREATOR = new Creator<UserForm>() {
        @Override
        public UserForm createFromParcel(Parcel in) {
            return new UserForm(in);
        }

        @Override
        public UserForm[] newArray(int size) {
            return new UserForm[size];
        }
    };

    /*public String getRole_Id() {
        return Role_Id;
    }

    public void setRole_Id(String role_Id) {
        Role_Id = role_Id;
    }
*/
    public UserForm(String emp_Mob, String password, String active_Status, String emp_Id, String role_Id, String emp_Name, String emp_Img, String emp_Email, String emp_Address, String emp_Adhar_Id, String user_Name, String role) {
        Emp_Mob = emp_Mob;
        Password = password;
        Active_Status = active_Status;
        Emp_Id = emp_Id;
       // Role_Id = role_Id;
        Emp_Name = emp_Name;
        Emp_Img = emp_Img;
        Emp_Email = emp_Email;
        Emp_Address = emp_Address;
        Emp_Adhar_Id = emp_Adhar_Id;
        User_Name = user_Name;
        this.role = role;
    }

    public String getEmp_Id() {
        return Emp_Id;
    }

    public void setEmp_Id(String emp_Id) {
        Emp_Id = emp_Id;
    }



    public String getActive_Status() {
        return Active_Status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setActive_Status(String active_Status) {
        Active_Status = active_Status;
    }

    public UserForm() {
    }

    public String getEmp_Name() {
        return Emp_Name;
    }

    public void setEmp_Name(String emp_Name) {
        Emp_Name = emp_Name;
    }

    public String getEmp_Img() {
        return Emp_Img;
    }

    public void setEmp_Img(String emp_Img) {
        Emp_Img = emp_Img;
    }

    public String getEmp_Email() {
        return Emp_Email;
    }

    public void setEmp_Email(String emp_Email) {
        Emp_Email = emp_Email;
    }

    public String getEmp_Address() {
        return Emp_Address;
    }

    public void setEmp_Address(String emp_Address) {
        Emp_Address = emp_Address;
    }

    public String getEmp_Adhar_Id() {
        return Emp_Adhar_Id;
    }

    public void setEmp_Adhar_Id(String emp_Adhar_Id) {
        Emp_Adhar_Id = emp_Adhar_Id;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getEmp_Mob() {
        return Emp_Mob;
    }

    public void setEmp_Mob(String emp_Mob) {
        Emp_Mob = emp_Mob;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Emp_Mob);
        dest.writeString(Password);
        dest.writeString(Active_Status);
       // dest.writeString(Role_Id);
        dest.writeString(Emp_Id);
        dest.writeString(Emp_Name);
        dest.writeString(Emp_Img);
        dest.writeString(Emp_Email);
        dest.writeString(Emp_Address);
        dest.writeString(Emp_Adhar_Id);
        dest.writeString(User_Name);
        dest.writeString(role);
    }
}
