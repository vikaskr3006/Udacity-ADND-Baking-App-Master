package com.example.udacity.vikaskumar.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.udacity.vikaskumar.bakingapp.R;
import com.example.udacity.vikaskumar.bakingapp.adapter.FoodItemAdapter;
import com.example.udacity.vikaskumar.bakingapp.idlingResource.SimpleIdlingResource;
import com.example.udacity.vikaskumar.bakingapp.pojo.Recipe;
import com.example.udacity.vikaskumar.bakingapp.retrofit.RecipeApi;
import com.example.udacity.vikaskumar.bakingapp.retrofit.RetrofitBuilder;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodItemFragment extends Fragment {

    @BindView(R.id.food_item_recycler_view) RecyclerView foodItemRecyclerView;

    @BindString(R.string.ALL_RECIPES) String ALL_RECIPES;

    SimpleIdlingResource idlingResource;

    // Mandatory constructor for instantiating the fragment
    public FoodItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_food_item, container, false);
        ButterKnife.bind(this, rootView);

        if (rootView.getTag() != null && rootView.getTag().equals(getString(R.string.tag_phone_land))){
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(),4);
            foodItemRecyclerView.setLayoutManager(layoutManager);
        }
        else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            foodItemRecyclerView.setLayoutManager(layoutManager);
        }

        RecipeApi recipeApi = RetrofitBuilder.Retrieve();
        Call<ArrayList<Recipe>> recipes = recipeApi.getRecipe();

        idlingResource = (SimpleIdlingResource)((FoodItemActivity)getActivity()).getIdlingResource();

        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        recipes.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                Integer statusCode = response.code();
                Log.v("status code: ", statusCode.toString());

                ArrayList<Recipe> recipesList = response.body();

                Bundle recipesBundle = new Bundle();
                recipesBundle.putParcelableArrayList(ALL_RECIPES, recipesList);

                FoodItemAdapter foodItemAdapter =new FoodItemAdapter(recipesList,getContext(),(FoodItemActivity)getActivity());
                foodItemRecyclerView.setAdapter(foodItemAdapter);

                if (idlingResource != null) {
                    idlingResource.setIdleState(true);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.v("http fail: ", t.getMessage());
            }
        });

        return rootView;
    }
}
