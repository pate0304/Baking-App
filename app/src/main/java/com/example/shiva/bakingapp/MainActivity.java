package com.example.shiva.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ListViewFragment listViewFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listViewFragment = new ListViewFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.frame, listViewFragment)
                .commit();


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (getSupportFragmentManager().findFragmentByTag("recipedetails") != null)
            getSupportFragmentManager().findFragmentByTag("recipedetails").setRetainInstance(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getSupportFragmentManager().findFragmentByTag("recipedetails") != null)
            getSupportFragmentManager().findFragmentByTag("recipedetails").getRetainInstance();

    }
}
