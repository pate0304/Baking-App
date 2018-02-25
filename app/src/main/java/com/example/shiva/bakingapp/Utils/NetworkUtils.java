package com.example.shiva.bakingapp.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by shiva on 2018-02-24.
 */

public interface  NetworkUtils {
    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Model>> getRecipies();



    String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";

    class Factory {
        //if instance is made use it or create new if doesnt !!
        private static NetworkUtils service;

        public static NetworkUtils getInstance() {

            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();

                service = retrofit.create(NetworkUtils.class);
                return service;
            } else {
                return service;
            }
        }

    }}
