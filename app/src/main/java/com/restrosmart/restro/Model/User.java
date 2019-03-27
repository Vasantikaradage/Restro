
package com.restrosmart.restro.Model;

public class User {

    private  String Emp_Id;
    private  String Emp_Name;
    private  String Emp_Img;
    private  String Emp_Mob;
    private  String Emp_Email;
    private  String Emp_Address;
    private  String Emp_Adhar_Id;
    private  String User_Name;


    public User(String emp_Id, String emp_Name, String emp_Img, String emp_Mob, String emp_Email, String emp_Address, String emp_Adhar_Id, String user_Name) {
        Emp_Id = emp_Id;
        Emp_Name = emp_Name;
        Emp_Img = emp_Img;
        Emp_Mob = emp_Mob;
        Emp_Email = emp_Email;
        Emp_Address = emp_Address;
        Emp_Adhar_Id = emp_Adhar_Id;
        User_Name = user_Name;
    }

    public User() {
    }

    public String getEmp_Id() {
        return Emp_Id;
    }

    public void setEmp_Id(String emp_Id) {
        Emp_Id = emp_Id;
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

    public String getEmp_Mob() {
        return Emp_Mob;
    }

    public void setEmp_Mob(String emp_Mob) {
        Emp_Mob = emp_Mob;
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
}
