package com.example.shiva.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shiva on 2018-03-14.
 */

public class DetailedFragment extends Fragment {
    public DetailedFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.listview_content, container, false);



        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
