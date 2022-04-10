package com.example.retrofitjava.service;

import com.example.retrofitjava.model.CryptoModel;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface CryptoAPI {

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    Call<List<CryptoModel>> getData();
}
