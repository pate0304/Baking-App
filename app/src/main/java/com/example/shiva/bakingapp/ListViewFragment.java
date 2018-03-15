package com.example.shiva.bakingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shiva.bakingapp.Adapters.RVAdapter;
import com.example.shiva.bakingapp.Utils.Model;
import com.example.shiva.bakingapp.Utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shiva on 2018-02-24.
 */

@SuppressLint("ValidFragment")
public class ListViewFragment extends android.support.v4.app.Fragment {

    RecyclerView recyclerView;

    public ArrayList<Model> arrayList;
    RVAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    public ListViewFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.listview_content, container, false);

        arrayList=new ArrayList<>();

        adapter = new RVAdapter();
        if (savedInstanceState == null) {
            getRecipies();

        } else {
            arrayList = (ArrayList<Model>) savedInstanceState.getSerializable("response");
            Log.d("SIZE", String.valueOf(arrayList.size()));
            adapter.setModelArrayList(arrayList);

        }



        recyclerView = rootView.findViewById(R.id.rv_list);
        if (getResources().getConfiguration().orientation == 1) {
            recyclerView.setLayoutManager((new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)));
        } else {
            recyclerView.setLayoutManager((new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)));
        }
        recyclerView.setHasFixedSize(true);


        recyclerView.setAdapter(adapter);

        return rootView;
    }


    private void getRecipies() {
        if (isOnline(this.getContext())) {
            NetworkUtils.Factory.getInstance().getRecipies().enqueue(new Callback<List<Model>>() {
                @Override
                public void onResponse(@NonNull Call<List<Model>> call, @NonNull Response<List<Model>> response) {
                    if (response.body() != null) {

                        arrayList.addAll(response.body());
                        adapter.setModelArrayList(arrayList);

                    }
                    Log.d("400models.size", String.valueOf(arrayList.size()));
                }

                @Override
                public void onFailure(@NonNull Call<List<Model>> call, Throwable t) {
                    Log.d("failure", t.getCause().toString());
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("response", this.arrayList);
        Log.d("arraylistvals", this.arrayList.get(0).getName());
    }

    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }
}
