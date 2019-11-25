package com.restrosmart.restrohotel.SuperAdmin.Models;

import java.util.ArrayList;

public class EmployeeSAHotelForm {
    private String HotelName;
    private ArrayList<EmployeeSAForm> employeeSAHotelForms;

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        HotelName = hotelName;
    }

    public ArrayList<EmployeeSAForm> getEmployeeSAHotelForms() {
        return employeeSAHotelForms;
    }

    public void setEmployeeSAHotelForms(ArrayList<EmployeeSAForm> employeeSAHotelForms) {
        this.employeeSAHotelForms = employeeSAHotelForms;
    }
}
