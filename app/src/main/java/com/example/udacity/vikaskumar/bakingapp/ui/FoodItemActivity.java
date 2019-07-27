package com.example.udacity.vikaskumar.bakingapp.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.udacity.vikaskumar.bakingapp.R;
import com.example.udacity.vikaskumar.bakingapp.adapter.FoodItemAdapter;
import com.example.udacity.vikaskumar.bakingapp.idlingResource.SimpleIdlingResource;
import com.example.udacity.vikaskumar.bakingapp.pojo.Recipe;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class FoodItemActivity extends AppCompatActivity implements FoodItemAdapter.FoodItemClickListener{

    @BindView(R.id.baking_app_toolbar)Toolbar myToolbar;
    @BindString(R.string.SELECTED_RECIPES) String SELECTED_RECIPES;

    @Nullable
    private SimpleIdlingResource mIdlingResource;
    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_food_item);
        ButterKnife.bind(this);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(R.string.app_name);

        // Get the IdlingResource instance
        getIdlingResource();
    }

    @Override
    public void onFoodItemClick(Recipe selectedFoodItem) {
        Bundle selectedRecipeBundle = new Bundle();
        Recipe selectedRecipe = selectedFoodItem;
        selectedRecipeBundle.putParcelable(SELECTED_RECIPES,selectedRecipe);

        final Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtras(selectedRecipeBundle);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
