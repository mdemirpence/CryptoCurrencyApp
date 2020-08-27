package com.mdemirpence.retrofitjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mdemirpence.retrofitjava.R;
import com.mdemirpence.retrofitjava.adapter.RecyclerViewAdapter;
import com.mdemirpence.retrofitjava.model.KriptoModel;
import com.mdemirpence.retrofitjava.service.KriptoAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<KriptoModel> kriptoModels;

    private String BASE_URL = "https://api.nomics.com/v1/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    CompositeDisposable compositeDisposable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://api.nomics.com/v1/prices?key=712e4f78252142ac7004e2b0afe616bd

        //RETROFİT  VE JSON

        recyclerView = findViewById(R.id.recyclerView);

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

         loadData();
    }

    private  void loadData() {


        final KriptoAPI kriptoAPI = retrofit.create(KriptoAPI.class);

        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(kriptoAPI.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse));


        /*

        Call<List<KriptoModel>> call = kriptoAPI.getData();

        call.enqueue(new Callback<List<KriptoModel>>() {
            @Override
            public void onResponse(Call<List<KriptoModel>> call, Response<List<KriptoModel>> response) {
                    if (response.isSuccessful()) {

                        List<KriptoModel> responseList = response.body();
                        kriptoModels =  new ArrayList<>(responseList);

                        //recyclerview

                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerViewAdapter = new RecyclerViewAdapter(kriptoModels);
                        recyclerView.setAdapter(recyclerViewAdapter);

                       // for (KriptoModel kriptoModel : kriptoModels)





                    }
            }

            @Override
            public void onFailure(Call<List<KriptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });

         */
    }

    private void handleResponse(List<KriptoModel> kriptoModelList ){


        kriptoModels =  new ArrayList<>(kriptoModelList);

        //recyclerview

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter = new RecyclerViewAdapter(kriptoModels);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }
}