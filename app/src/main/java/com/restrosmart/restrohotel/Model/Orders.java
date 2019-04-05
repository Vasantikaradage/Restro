package com.restrosmart.restrohotel.Model;

public class Orders {

    private String cust_mob_no,order_id,tot_bill,time,menu_name,menu_qty,menu_price,msg;

    public String getCust_mob_no() {
        return cust_mob_no;
    }

    public void setCust_mob_no(String cust_mob_no) {
        this.cust_mob_no = cust_mob_no;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTot_bill() {
        return tot_bill;
    }

    public void setTot_bill(String tot_bill) {
        this.tot_bill = tot_bill;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_qty() {
        return menu_qty;
    }

    public void setMenu_qty(String menu_qty) {
        this.menu_qty = menu_qty;
    }

    public String getMenu_price() {
        return menu_price;
    }

    public void setMenu_price(String menu_price) {
        this.menu_price = menu_price;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
