package com.restrosmart.restro.Model;

/**
 * Created by yarolegovich on 08.03.2017.
 */

public class Liquor {

    private final String cityName;
    private final int cityIcon;



    public Liquor(String cityName, int cityIcon) {
        this.cityName = cityName;
        this.cityIcon = cityIcon;
    }

    public String getCityName() {
        return cityName;
    }

    public int getCityIcon() {
        return cityIcon;
    }
}
