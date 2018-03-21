package com.example.shiva.bakingapp;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

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
    Button ingredientsbtn;

    public RecipeDetailFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview, container, false);


        if (savedInstanceState != null) {
            stepList = savedInstanceState.getParcelableArrayList("stepList");
        } else {
            stepList = new ArrayList<>();
            stepList = getArguments().getParcelableArrayList("stepList");
        }

        String recipeName = getArguments().getString("name");

        getActivity().setTitle(recipeName);
        setRetainInstance(true);
        recipeDetailRV = new RecipeDetailRV();
        ingredientList = new ArrayList<>();
        ingredientList = getArguments().getParcelableArrayList("ingredientList");
        recyclerView = rootView.findViewById(R.id.simpleRecyclerView);
        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        ingredientsbtn = rootView.findViewById(R.id.ingredientsBtn);


        ingredientsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateDialog(ingredientList);
            }


        });


        recipeDetailRV.setList(stepList);
        recipeDetailRV.name = getActivity().getTitle().toString();

        recyclerView.setAdapter(recipeDetailRV);

        return rootView;
    }

    /**
     * Method to display ingredients for selected recpie
     *
     * @param ingredientList
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void populateDialog(List<Model.Ingredient> ingredientList) {

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        Log.d("size:", String.valueOf(ingredientList.size()));
        for (int i = 0; i < ingredientList.size(); i++) {
            arrayAdapter.add(i + ": " + ingredientList.get(i).getQuantity() + " " + ingredientList.get(i).getMeasure()
                    + " " + ingredientList.get(i).getIngredient() + "\n");
        }

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Ingredients for " + getActivity().getTitle())
                .setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.setPositiveButton("Show steps", null);


        final AlertDialog alertDialog = builder.create();
        alertDialog.create();
        Button pButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        pButton.setTextColor(getResources().getColor(R.color.colorPrimaryDark));


        builder.show();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("stepList", (ArrayList<? extends Parcelable>) stepList);
    }


}
