package com.restrosmart.restro.Model;

/**
 * Created by SHREE on 26/11/2018.
 */

public class BranchForm {

    private  int branchId;
    private  String branchName;

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Override
    public String toString() {
        return branchName;
    }
}
