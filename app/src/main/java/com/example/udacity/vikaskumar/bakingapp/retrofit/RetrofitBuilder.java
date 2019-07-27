package com.example.udacity.vikaskumar.bakingapp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitBuilder {

    static RecipeApi recipeApi;

    public static RecipeApi Retrieve() {

        Gson gson = new GsonBuilder().create();

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();


        recipeApi = new Retrofit.Builder()
                .baseUrl(RecipeApi.RECIPE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(httpClientBuilder.build())
                .build().create(RecipeApi.class);


        return recipeApi;
    }
}
