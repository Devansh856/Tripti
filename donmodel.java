package com.example.tripti;

public class donmodel {

    String name,mobile,item,qty,address,date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public donmodel(String name, String mobile, String item, String qty, String address, String date) {
        this.name = name;
        this.mobile = mobile;
        this.item = item;
        this.qty = qty;
        this.address = address;
        this.date=date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getItem() {
        return item;
    }

    public String getQty() {
        return qty;
    }

    public String getAddress() {
        return address;
    }
}
