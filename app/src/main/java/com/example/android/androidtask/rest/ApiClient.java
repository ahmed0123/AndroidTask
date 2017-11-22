package com.example.android.androidtask.rest;

import com.example.android.androidtask.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by world on 2017/11/21.
 */

public class ApiClient {


    public static Retrofit retrofit = null;

    public static Retrofit getClient (){


        if(retrofit == null){
            retrofit  = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit;
    }
}
