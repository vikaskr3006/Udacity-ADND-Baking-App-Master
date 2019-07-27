package com.example.udacity.vikaskumar.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.udacity.vikaskumar.bakingapp.R;
import com.example.udacity.vikaskumar.bakingapp.pojo.Ingredient;
import com.example.udacity.vikaskumar.bakingapp.pojo.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailIngredientsAdapter
        extends RecyclerView.Adapter<RecipeDetailIngredientsAdapter.RecyclerViewHolder>{

    Recipe recipe;
    Context context;
    ArrayList<Ingredient> ingredients;

    public RecipeDetailIngredientsAdapter(Recipe recipe, Context context) {
        this.recipe = recipe;
        this.context = context;
        ingredients = recipe.getIngredients();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.card_view_ingredients;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.ingredientTv.setText(ingredients.get(i).getIngredient());
        recyclerViewHolder.quantityTv.setText
                (String.valueOf(ingredients.get(i).getQuantity()) + " " + ingredients.get(i).getMeasure());
    }

    @Override
    public int getItemCount() {
        return ingredients != null ? ingredients.size():0 ;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_tv) TextView ingredientTv;
        @BindView(R.id.quantity_tv) TextView quantityTv;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
