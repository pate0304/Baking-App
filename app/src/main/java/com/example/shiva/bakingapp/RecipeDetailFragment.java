package com.example.shiva.bakingapp;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shiva.bakingapp.Adapters.RecipeDetailRV;
import com.example.shiva.bakingapp.Utils.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiva on 2018-03-14.
 */

public class RecipeDetailFragment extends Fragment {
    RecipeDetailRV recipeDetailRV;
    List<Model.Ingredient> ingredientList;
    List<Model.Step> stepList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;


    public RecipeDetailFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview, container, false);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) rootView.findViewById(R.id.collapsableToolbar);

        if (savedInstanceState != null) {
            stepList = savedInstanceState.getParcelableArrayList("stepList");
        } else {

            stepList = new ArrayList<>();

            stepList = getArguments().getParcelableArrayList("stepList");
        }

        String recipeName = getArguments().getString("name");

        toolbar.setTitle(recipeName);
        setRetainInstance(true);
        recipeDetailRV = new RecipeDetailRV();
        ingredientList = new ArrayList<>();
        ingredientList = getArguments().getParcelableArrayList("ingredientList");
        recyclerView = rootView.findViewById(R.id.simpleRecyclerView);
        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(mLayoutManager);

//        TextView textView = rootView.findViewById(R.id.ingredientsTV);
        String s = "INGREDIENTS:" + "\n";
        for (int i = 0; i < ingredientList.size(); i++) {
            s = s + (i + " :" + ingredientList.get(i).getIngredient() + "\n");
        }

//        textView.setText(s);

        recipeDetailRV.setList(stepList);

        recyclerView.setAdapter(recipeDetailRV);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("stepList", (ArrayList<? extends Parcelable>) stepList);
    }


}
