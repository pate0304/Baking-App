package com.example.shiva.bakingapp;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.shiva.bakingapp.Utils.Model;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;
import java.util.List;

public class viewRecipe extends Fragment implements View.OnClickListener {
    private List<Model.Step> stepList;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer simpleExoPlayer;
    private int stepNum;
    private TextView shortDesc, description;
    private Button left, right;
    String name;
    private Model.Step step;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_recipe_layout, container, false);

        if (savedInstanceState != null) {
            stepList = savedInstanceState.getParcelableArrayList("stepList");
            stepNum = savedInstanceState.getInt("stepNum");
        } else {
            stepList = new ArrayList<>();
            stepList = getArguments().getParcelableArrayList("stepList");
            stepNum = getArguments().getInt("stepNum");
            name = getArguments().getString("name");
        }


        shortDesc = rootView.findViewById(R.id.shortDescription);
        description = rootView.findViewById(R.id.description);
        left = rootView.findViewById(R.id.left);
        right = rootView.findViewById(R.id.right);

        left.setOnClickListener(this);
        right.setOnClickListener(this);

        validateStepsNumber();

        // defining step number for the fragment to work with UI
        step = new Model.Step();
        step = stepList.get(stepNum);

        shortDesc.setText(step.getShortDescription());
        description.setText(step.getDescription());
        assert savedInstanceState != null;
        getActivity().setTitle(name);

        simpleExoPlayerView = rootView.findViewById(R.id.player);

        simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.brownie));
        Log.d("MAIN ON ARRIVE: ", String.valueOf(getFragmentManager().getBackStackEntryCount()));
        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();

        if (getView() == null) {
            return;
        }
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (keyEvent.getAction() == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_BACK) {
                    getFragmentManager().popBackStack("stack", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
//        String name = getFragmentManager().getBackStackEntryAt(3).getName();

    }

    @SuppressLint("SetTextI18n")
    private void validateStepsNumber() {
        int size = stepList.size() - 1;
        if (stepNum == 0) {
            left.setClickable(false);
            left.setVisibility(View.INVISIBLE);
        } else {
            right.setText("Step " + stepNum);
        }

        if (stepNum == size) {
            right.setClickable(false);
            right.setVisibility(View.INVISIBLE);
            left.setText("Step " + (size - 1));
        } else {
            left.setText("Step " + (stepNum - 1));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("stepList", (ArrayList<? extends Parcelable>) stepList);
        outState.putInt("stepNum", stepNum);
    }


    @Override
    public void onClick(View view) {


        FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
        switch (view.getId()) {
            case R.id.left:
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putInt("stepNum", stepNum - 1);
                bundle.putParcelableArrayList("stepList", (ArrayList<? extends Parcelable>) stepList);
                viewRecipe viewRecipe = new viewRecipe();
                viewRecipe.setArguments(bundle);
                fragmentManager.beginTransaction()
//                        .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_from_right)
                        .replace(R.id.frame, viewRecipe, "navButtons")
                        .addToBackStack("navButtons")
                        .commit();
                Log.d("left: ", String.valueOf(getFragmentManager().getBackStackEntryCount()));
                break;
            case R.id.right:
                Bundle bundle2 = new Bundle();
                bundle2.putString("name", name);
                bundle2.putInt("stepNum", stepNum + 1);
                bundle2.putParcelableArrayList("stepList", (ArrayList<? extends Parcelable>) stepList);
                viewRecipe viewRecipe2 = new viewRecipe();
                viewRecipe2.setArguments(bundle2);
                fragmentManager.beginTransaction()
//                        .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.frame, viewRecipe2, "navButtons")
                        .addToBackStack("navButtons")
                        .commit();
                Log.d("right: ", String.valueOf(getFragmentManager().getBackStackEntryCount()));
                break;
        }
    }
}
