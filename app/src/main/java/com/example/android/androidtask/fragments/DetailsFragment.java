package com.example.android.androidtask.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.androidtask.R;
import com.example.android.androidtask.model.Article;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    @BindView(R.id.details_image)
    ImageView backgroundImage;
    @BindView(R.id.details_date)
    TextView date;
    @BindView(R.id.details_title)
    TextView title;
    @BindView(R.id.details_author)
    TextView author;
    @BindView(R.id.details_description)
    TextView description;
    @BindView(R.id.button)
    Button open_Button;
    Article article;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);


        showBackButton();

        Typeface txtFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Lato-Regular.ttf");
        Typeface buttonFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Lato-Light.ttf");

        ButterKnife.bind(this, view);
        title.setTypeface(txtFont);
        open_Button.setTypeface(buttonFont);
        Bundle bundle = getArguments();
        article = bundle.getParcelable("EXTRA_ARTICLE_ID");


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Picasso.with(getActivity()).load(article.getUrlToImage()).into(backgroundImage);
        try {
            date.setText(formatDate(article.getPublishedAt()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        title.setText(article.getTitle());
        author.setText("By "+article.getAuthor());
        description.setText(article.getDescription());
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
    public void showBackButton() {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
