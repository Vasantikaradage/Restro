package com.restrosmart.restro.Model;

import java.util.ArrayList;

/**
 * Created by SHREE on 04/10/2018.
 */

public class MenuForm {

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    ArrayList<MenuDisplayForm> menuDisplayForms;

    public ArrayList<MenuDisplayForm> getMenuDisplayForms() {
        return menuDisplayForms;
    }

    public void setMenuDisplayForms(ArrayList<MenuDisplayForm> menuDisplayForms) {
        this.menuDisplayForms = menuDisplayForms;
    }
}
