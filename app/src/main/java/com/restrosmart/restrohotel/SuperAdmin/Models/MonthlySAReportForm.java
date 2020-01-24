package com.restrosmart.restrohotel.SuperAdmin.Models;

public class MonthlySAReportForm {
    private  String categoryFood,categoryLiquors,week;

    public String getCategoryFood() {
        return categoryFood;
    }

    public void setCategoryFood(String categoryFood) {
        this.categoryFood = categoryFood;
    }

    public String getCategoryLiquors() {
        return categoryLiquors;
    }

    public void setCategoryLiquors(String categoryLiquors) {
        this.categoryLiquors = categoryLiquors;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
