package com.example.retrofitjava.model;

import com.google.gson.annotations.SerializedName;

public class CryptoModel {

    @SerializedName("currency")
    private String currency;

    @SerializedName("price")
    private String price;

    public String getCurrency() {
        return currency;
    }

    public String getPrice() {
        return price;
    }
}
