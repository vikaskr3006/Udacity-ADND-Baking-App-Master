package com.example.udacity.vikaskumar.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.udacity.vikaskumar.bakingapp.R;
import com.example.udacity.vikaskumar.bakingapp.adapter.RecipeDetailIngredientsAdapter;
import com.example.udacity.vikaskumar.bakingapp.adapter.RecipeDetailStepsAdapter;
import com.example.udacity.vikaskumar.bakingapp.pojo.Ingredient;
import com.example.udacity.vikaskumar.bakingapp.pojo.Recipe;
import com.example.udacity.vikaskumar.bakingapp.widget.UpdateBakingService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment {

    Recipe recipe;
    String recipeName;
    @BindView(R.id.ingredients_rv) RecyclerView ingredientsRv;
    @BindView(R.id.recipe_steps_rv) RecyclerView stepDetailRv;
    @BindView(R.id.add_to_widget_button) Button widgetButton;

    @BindString(R.string.SELECTED_RECIPES) String SELECTED_RECIPES;
    @BindString(R.string.RECIPE_NAME) String RECIPE_NAME;

    // Mandatory constructor for instantiating the fragment
    public RecipeDetailFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, rootView);

        if(savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(SELECTED_RECIPES);

        }
        else {
            recipe = getArguments().getParcelable(SELECTED_RECIPES);
        }

        recipeName = recipe.getName();


        LinearLayoutManager mLayoutManager=new LinearLayoutManager(getContext());
        ingredientsRv.setLayoutManager(mLayoutManager);

        RecipeDetailIngredientsAdapter recipeDetailIngredientsAdapter = new RecipeDetailIngredientsAdapter(recipe, getContext());
        ingredientsRv.setAdapter(recipeDetailIngredientsAdapter);

        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getContext());
        stepDetailRv.setLayoutManager(mLayoutManager1);

        RecipeDetailStepsAdapter mRecipeDetailStepsAdapter =
                new RecipeDetailStepsAdapter(recipe, getContext(), (RecipeDetailActivity)getActivity());
        stepDetailRv.setAdapter(mRecipeDetailStepsAdapter);


        widgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> recipeIngredientsForWidgets= new ArrayList<>();
                String widgetTitle = recipeName.toUpperCase() + " (Ingredients):\n";
                recipeIngredientsForWidgets.add(widgetTitle);
                List<Ingredient> ingredients = recipe.getIngredients();
                for(Ingredient ingredient : ingredients){
                    String widgetData = ingredient.getIngredient() + "\n\t" +
                            "Quantity: "+ingredient.getQuantity().toString() + " " + ingredient.getMeasure();
                    recipeIngredientsForWidgets.add(widgetData);
                }
                UpdateBakingService.startBakingService(getContext(),recipeIngredientsForWidgets);

                Toast.makeText(v.getContext(), getString(R.string.widget_success_msg), Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelable(SELECTED_RECIPES, recipe);
        currentState.putString(RECIPE_NAME, recipeName);
    }
}
