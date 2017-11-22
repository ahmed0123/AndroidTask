package com.example.android.androidtask.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.androidtask.R;
import com.example.android.androidtask.adapter.ArticlesAdpater;
import com.example.android.androidtask.model.Article;
import com.example.android.androidtask.model.ResultArticles;
import com.example.android.androidtask.rest.ApiClient;
import com.example.android.androidtask.rest.ArticlesApi;
import com.example.android.androidtask.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements ArticlesAdpater.RecyclerViewItemClickListener {

    private ArticlesAdpater adpater;
    private List<Article> articlesList;
    private RecyclerView recyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void itemClicked(Article article) {
        ((Communicator) getActivity()).onResponse(article);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getArticlesList();

        return view;
    }

    private void getArticlesList() {
        ArticlesApi articlesApi = ApiClient.getClient().create(ArticlesApi.class);
        Call<ResultArticles> articlesConn = articlesApi.getAllArticles(Constants.SOURCE, Constants.API_KEY);

        articlesConn.enqueue(new Callback<ResultArticles>() {
            @Override
            public void onResponse(Call<ResultArticles> call, Response<ResultArticles> response) {

                articlesList = response.body().getArticles();
                adpater = new ArticlesAdpater(getContext(),articlesList,HomeFragment.this);
                recyclerView.setAdapter(adpater);
            }

            @Override
            public void onFailure(Call<ResultArticles> call, Throwable t) {

                Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                Log.e("Error", t.getMessage());

            }
        });
    }



    public interface Communicator {

        void onResponse(Article article);
    }
}
