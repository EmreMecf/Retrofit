package com.example.retrofitjava.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.retrofitjava.R;
import com.example.retrofitjava.adater.RecyclerViewAdapter;
import com.example.retrofitjava.model.CryptoModel;
import com.example.retrofitjava.service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL="https://raw.githubusercontent.com/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);

        Gson gson=new GsonBuilder().setLenient().create();
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        loadData();

    }

    private void loadData(){
       final CryptoAPI cryptoAPI=retrofit.create(CryptoAPI.class);
       compositeDisposable=new CompositeDisposable();
       compositeDisposable.add(cryptoAPI.getData()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(this::handleResponse));




       /*
        Call<List<CryptoModel>> call= (Call<List<CryptoModel>>) cryptoAPI.getData();

        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()){
                    List<CryptoModel> responseList= response.body();
                    cryptoModels=new ArrayList<>(responseList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter=new RecyclerViewAdapter(cryptoModels);
                    recyclerView.setAdapter(recyclerViewAdapter);

                }

            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {

            }
        });

        */

    }
    private void handleResponse(List<CryptoModel> cryptoModelList){
        cryptoModels=new ArrayList<>(cryptoModelList);

       //Recycler View
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter=new RecyclerViewAdapter(cryptoModels);
        recyclerView.setAdapter(recyclerViewAdapter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}



//https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json