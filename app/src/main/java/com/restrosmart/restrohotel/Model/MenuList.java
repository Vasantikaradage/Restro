
package com.restrosmart.restrohotel.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenuList {

    @SerializedName("Pc_Id")
    @Expose
    private Integer pcId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("menu")
    @Expose
    private List<Menu> menu = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MenuList() {
    }

    /**
     * 
     * @param pcId
     * @param menu
     * @param name
     */
    public MenuList(Integer pcId, String name, List<Menu> menu) {
        super();
        this.pcId = pcId;
        this.name = name;
        this.menu = menu;
    }

    public Integer getPcId() {
        return pcId;
    }

    public void setPcId(Integer pcId) {
        this.pcId = pcId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

}
