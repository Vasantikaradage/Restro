package com.restrosmart.restro.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SHREE on 10/13/2018.
 */

public class EmployeeRole {

    @SerializedName("Role_Id")
    @Expose
    private Integer roleId;
    @SerializedName("Role")
    @Expose
    private String role;

    /**
     * No args constructor for use in serialization
     *
     */
    public EmployeeRole() {
    }

    /**
     *
     * @param role
     * @param roleId
     */
    public EmployeeRole(Integer roleId, String role) {
        super();
        this.roleId = roleId;
        this.role = role;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
