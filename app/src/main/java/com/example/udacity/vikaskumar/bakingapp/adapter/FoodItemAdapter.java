package com.example.udacity.vikaskumar.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.udacity.vikaskumar.bakingapp.R;
import com.example.udacity.vikaskumar.bakingapp.pojo.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.RecyclerViewHolder> {

    ArrayList<Recipe> mRecipes;
    Context mContext;
    final private FoodItemClickListener mOnClickListener;

    public interface FoodItemClickListener {
        void onFoodItemClick(Recipe selectedFoodItem);
    }

    public FoodItemAdapter(ArrayList<Recipe> recipes, Context context, FoodItemClickListener listener){
        mRecipes = recipes;
        mContext = context;
        mOnClickListener = listener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForViewItem = R.layout.card_view_food_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForViewItem, viewGroup, shouldAttachToParentImmediately);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int position) {
        recyclerViewHolder.mRecipeNameTv.setText(mRecipes.get(position).getName());

        String imageUrl=mRecipes.get(position).getImage();

//        if (imageUrl!="") {
//            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
//            Picasso.with(mContext).load(builtUri).into(recyclerViewHolder.mFoodItemIv);
//        }else{
//            recyclerViewHolder.mFoodItemIv.setVisibility(View.GONE);
//        }

        String recipeName = mRecipes.get(position).getName();
        int imageId = 0;

        switch(recipeName){
            case "Nutella Pie" :
                imageId = R.drawable.nutella_pie;
                recyclerViewHolder.mFoodItemIv.setImageResource(imageId);
                break;
            case "Brownies":
                imageId = R.drawable.brownies;
                recyclerViewHolder.mFoodItemIv.setImageResource(imageId);
                break;
            case "Yellow Cake":
                imageId = R.drawable.yellow_cake;
                recyclerViewHolder.mFoodItemIv.setImageResource(imageId);
                break;
            case "Cheesecake":
                imageId = R.drawable.cheesecake;
                recyclerViewHolder.mFoodItemIv.setImageResource(imageId);
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mRecipes != null ? mRecipes.size() : 0;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.food_item_iv) ImageView mFoodItemIv;
        @BindView(R.id.recipe_name_tv) TextView mRecipeNameTv;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int selectedFoodItemId = getAdapterPosition();
            mOnClickListener.onFoodItemClick(mRecipes.get(selectedFoodItemId));
        }
    }
}
