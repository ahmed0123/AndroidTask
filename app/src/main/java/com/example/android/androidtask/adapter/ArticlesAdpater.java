package com.example.android.androidtask.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.androidtask.R;
import com.example.android.androidtask.model.Article;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by world on 2017/11/21.
 */

public class ArticlesAdpater extends RecyclerView.Adapter<ArticlesAdpater.ArticleViewHolder> {


    private Context context;
    private RecyclerViewItemClickListener itemListiner;
    private List<Article> articlesList;

    public ArticlesAdpater(Context context, List<Article> articlesList, RecyclerViewItemClickListener itemListiner) {
        this.context = context;
        this.articlesList = articlesList;
        this.itemListiner = itemListiner;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_recycle_article, parent, false);
        return new ArticleViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {

        final Article article = articlesList.get(position);
        Picasso.with(context).load(article.getUrlToImage()).into(holder.poster_image_view);
        holder.title_txt.setText(article.getTitle());
        holder.author_txt.setText("By "+article.getAuthor());
        try {
            holder.date_txt.setText(formatDate(article.getPublishedAt()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public interface RecyclerViewItemClickListener {

        void itemClicked(Article article);
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {

        ImageView poster_image_view;
        TextView title_txt, author_txt, date_txt;


        public ArticleViewHolder(View itemView) {
            super(itemView);
            poster_image_view = itemView.findViewById(R.id.poster_image);
            title_txt = itemView.findViewById(R.id.title);
            author_txt = itemView.findViewById(R.id.author);
            date_txt = itemView.findViewById(R.id.published_at);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemListiner.itemClicked(articlesList.get(getAdapterPosition()));
                }
            });
        }


    }
    private String formatDate(String returenedDate) throws ParseException {
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-dd-MM'T'HH:mm:ss'Z'", Locale.ENGLISH);
        SimpleDateFormat destFormat = new SimpleDateFormat("MMM d, yyyy ");

        Date date = null;
        try {
            date = sourceFormat.parse(returenedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
       String formattedDate = destFormat.format(date);

        return formattedDate;

    }
}

