package com.example.udacity.vikaskumar.bakingapp.retrofit;

import com.example.udacity.vikaskumar.bakingapp.pojo.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeApi {

    final String RECIPE_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipe();
}
