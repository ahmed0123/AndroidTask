package com.example.android.androidtask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by world on 2017/11/21.
 */

public class ResultArticles {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("sortBy")
    @Expose
    private String sortBy;
    @SerializedName("articles")
    @Expose
    private List<Article> articles ;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
