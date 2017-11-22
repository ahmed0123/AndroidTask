package com.example.android.androidtask.rest;

import com.example.android.androidtask.model.Article;
import com.example.android.androidtask.model.ResultArticles;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by world on 2017/11/21.
 */

public interface ArticlesApi {

    @GET("articles")
    Call<ResultArticles> getAllArticles(@Query("source") String source , @Query("apiKey") String api_key);
}
