package com.example.android.androidtask.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.androidtask.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class EMagazineFragment extends Fragment {


    public EMagazineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_emagazine, container, false);
    }

}
