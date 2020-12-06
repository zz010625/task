package com.example.a6_lv5;

public class Bill {
    private String time, consumptionCategory, price;

    public Bill(String time, String consumptionCategory, String price) {
        this.time = time;
        this.consumptionCategory = consumptionCategory;
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public String getConsumptionCategory() {
        return consumptionCategory;
    }

    public String getPrice() {
        return price;
    }
}
