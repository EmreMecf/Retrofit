package com.example.retrofitjava.service;

import com.example.retrofitjava.model.CryptoModel;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface CryptoAPI {

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    Observable<List<CryptoModel>> getData();
}
