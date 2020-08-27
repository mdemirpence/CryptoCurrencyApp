package com.mdemirpence.retrofitjava.service;

import com.mdemirpence.retrofitjava.model.KriptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface KriptoAPI {

    //get, post, uptade, delete

    @GET("prices?key=712e4f78252142ac7004e2b0afe616bd")


    Observable<List<KriptoModel>> getData();



   //Call<List<KriptoModel>> getData();

}
