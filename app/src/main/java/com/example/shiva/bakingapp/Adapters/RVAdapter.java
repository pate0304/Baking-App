package com.example.shiva.bakingapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shiva.bakingapp.R;
import com.example.shiva.bakingapp.RecipeDetailFragment;
import com.example.shiva.bakingapp.Utils.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiva on 2018-02-24.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.containerClass> {

    private ArrayList<Model> models;
    int[] imageResourceIds = new int[4];

    public RVAdapter() {
        models = new ArrayList<>();

    }

    public void setModelArrayList(ArrayList<Model> modelArrayList) {

        this.models = modelArrayList;
        notifyDataSetChanged();
    }

    @Override
    public containerClass onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        int layoutIdForListItem = R.layout.layout_rv_cardview;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        imageResourceIds[0] = Integer.parseInt(String.valueOf(R.drawable.nutella));
        imageResourceIds[1] = Integer.parseInt(String.valueOf(R.drawable.brownie));
        imageResourceIds[2] = Integer.parseInt(String.valueOf(R.drawable.yellowcake));
        imageResourceIds[3] = Integer.parseInt(String.valueOf(R.drawable.cheesecake));

        return new containerClass(view);


    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(containerClass holder, int position) {
        holder.imageView.setImageResource(imageResourceIds[position]);
        holder.textView.setText(models.get(position).getName());

    }


    @Override
    public int getItemCount() {
        return models.size();
    }

    class containerClass extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        containerClass(final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cv_imageView);
            textView = itemView.findViewById(R.id.cv_textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<Model.Ingredient> ingredientList = models.get(getAdapterPosition()).getIngredients();
                    List<Model.Step> stepList = models.get(getAdapterPosition()).getSteps();


                    RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
                    Bundle b = new Bundle();
                    b.putParcelableArrayList("ingredientList", (ArrayList<? extends Parcelable>) ingredientList);
                    b.putParcelableArrayList("stepList", (ArrayList<? extends Parcelable>) stepList);
                    b.putString("name",models.get(getAdapterPosition()).getName());
                    recipeDetailFragment.setArguments(b);
                    FragmentManager fragmentManager = ((AppCompatActivity) itemView.getContext()).getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
//                            .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left)
                            .replace(R.id.frame, recipeDetailFragment)
                            .addToBackStack(null)
                            .commit();

                }
            });
        }
    }
}
