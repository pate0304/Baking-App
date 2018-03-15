package com.example.shiva.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shiva.bakingapp.R;
import com.example.shiva.bakingapp.Utils.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiva on 2018-03-14.
 */

public class RecipeDetailRV extends RecyclerView.Adapter<RecipeDetailRV.holderClass> {
    List<Model.Step> stepList = new ArrayList<>();

    @Override
    public RecipeDetailRV.holderClass onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_detail_lview;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipeDetailRV.holderClass(view);
    }

    @Override
    public void onBindViewHolder(RecipeDetailRV.holderClass holder, int position) {
        holder.textView.setText(stepList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public void setList(List<Model.Step> list) {
        this.stepList = list;
        notifyDataSetChanged();
    }

    public class holderClass extends RecyclerView.ViewHolder {
        TextView textView;

        public holderClass(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewRVContent);

        }
    }


}
