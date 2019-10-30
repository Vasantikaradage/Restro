package com.restrosmart.restrohotel.SuperAdmin.Models;

public class HotelTypeForm {
    private  int hotelTypeId;
    private  String hotelTypeName;

    public int getHotelTypeId() {
        return hotelTypeId;
    }

    public void setHotelTypeId(int hotelTypeId) {
        this.hotelTypeId = hotelTypeId;
    }

    public String getHotelTypeName() {
        return hotelTypeName;
    }

    public void setHotelTypeName(String hotelTypeName) {
        this.hotelTypeName = hotelTypeName;
    }

    @Override
    public String toString() {
        return hotelTypeName;
    }
}
