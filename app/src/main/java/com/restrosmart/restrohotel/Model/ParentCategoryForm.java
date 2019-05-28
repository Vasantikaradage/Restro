package com.restrosmart.restrohotel.Model;

/**
 * Created by SHREE on 30/10/2018.
 */

public class ParentCategoryForm {
    private  int Pc_id;
    private  String Name;
    private  String image;

    public int getPc_id() {
        return Pc_id;
    }

    public void setPc_id(int pc_id) {
        Pc_id = pc_id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return Name;
    }
}
