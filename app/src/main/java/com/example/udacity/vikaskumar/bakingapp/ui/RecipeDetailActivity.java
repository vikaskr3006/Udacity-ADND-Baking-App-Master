package com.example.udacity.vikaskumar.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.udacity.vikaskumar.bakingapp.R;
import com.example.udacity.vikaskumar.bakingapp.adapter.RecipeDetailStepsAdapter;
import com.example.udacity.vikaskumar.bakingapp.pojo.Recipe;
import com.example.udacity.vikaskumar.bakingapp.pojo.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeDetailStepsAdapter.ListItemClickListener,
        RecipeStepDetailFragment.ListItemClickListener {

    Recipe recipe;
    String recipeName;

    @BindView(R.id.baking_app_toolbar) Toolbar myToolbar;

    @BindString(R.string.SELECTED_RECIPES) String SELECTED_RECIPES;
    @BindString(R.string.STACK_RECIPE_DETAIL) String STACK_RECIPE_DETAIL;
    @BindString(R.string.STACK_RECIPE_STEP_DETAIL) String STACK_RECIPE_STEP_DETAIL;
    @BindString(R.string.SELECTED_STEPS) String SELECTED_STEPS;
    @BindString(R.string.SELECTED_INDEX) String SELECTED_INDEX;
    @BindString(R.string.RECIPE_NAME) String RECIPE_NAME;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {

            Bundle selectedRecipeBundle = getIntent().getExtras();

            recipe = selectedRecipeBundle.getParcelable(SELECTED_RECIPES);
            recipeName = recipe.getName();

            final RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(selectedRecipeBundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_fragment_container, fragment).addToBackStack(STACK_RECIPE_DETAIL)
                    .commit();

            if (findViewById(R.id.recipe_linear_layout).getTag() != null &&
                    findViewById(R.id.recipe_linear_layout).getTag().equals(getString(R.string.tag_tablet_land))) {

                final RecipeStepDetailFragment fragment2 = new RecipeStepDetailFragment();
                fragment2.setArguments(selectedRecipeBundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_detail_fragment_container_2, fragment2).addToBackStack(STACK_RECIPE_STEP_DETAIL)
                        .commit();

            }
        } else {
            recipeName= savedInstanceState.getString(RECIPE_NAME);
        }

        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(recipeName);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                if (findViewById(R.id.recipe_detail_fragment_container_2)==null) {
                    if (fm.getBackStackEntryCount() > 1) {
                        //go back to "Recipe Detail" screen
                        fm.popBackStack(STACK_RECIPE_DETAIL, 0);
                    } else if (fm.getBackStackEntryCount() > 0) {
                        //go back to "Food Item" screen
                        finish();
                    }
                }
                else {
                    //go back to "Food Item" screen
                    finish();
                }
            }
        });
    }


    @Override
    public void onListItemClick(List<Step> stepsOut, int selectedItemIndex, String recipeName) {

        final RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        getSupportActionBar().setTitle(recipeName);

        Bundle stepBundle = new Bundle();
        stepBundle.putParcelableArrayList(SELECTED_STEPS,(ArrayList<Step>) stepsOut);
        stepBundle.putInt(SELECTED_INDEX,selectedItemIndex);
        stepBundle.putString(RECIPE_NAME,recipeName);
        fragment.setArguments(stepBundle);

        if (findViewById(R.id.recipe_linear_layout).getTag() != null &&
                findViewById(R.id.recipe_linear_layout).getTag().equals(getString(R.string.tag_tablet_land))) {
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_fragment_container_2, fragment).addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit();

        }
        else {
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_fragment_container, fragment).addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(RECIPE_NAME,recipeName);
    }
}
