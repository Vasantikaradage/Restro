package com.restrosmart.restrohotel.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SHREE on 16/11/2018.
 */

public class RoleForm {
    @SerializedName("Role_Id")
    @Expose
    private Integer Role_Id;
    @SerializedName("Role")
    @Expose
    private String Role;

    public int getRole_Id() {
        return Role_Id;
    }

    public void setRole_Id(int role_Id) {
        Role_Id = role_Id;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    @Override
    public String toString() {
        return Role;
    }
}
