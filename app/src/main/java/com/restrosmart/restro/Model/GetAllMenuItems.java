
package com.restrosmart.restro.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllMenuItems {

    @SerializedName("allmenu")
    @Expose
    private Allmenu allmenu;

    private List<MenuList> menuList = null;

    public List<MenuList> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuList> menuList) {
        this.menuList = menuList;
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetAllMenuItems() {
    }

    /**
     * 
     * @param allmenu
     */
    public GetAllMenuItems(Allmenu allmenu) {
        super();
        this.allmenu = allmenu;
    }

    public Allmenu getAllmenu() {
        return allmenu;
    }

    public void setAllmenu(Allmenu allmenu) {
        this.allmenu = allmenu;
    }

}
