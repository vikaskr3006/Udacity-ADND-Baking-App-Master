package com.example.udacity.vikaskumar.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.udacity.vikaskumar.bakingapp.R;
import com.example.udacity.vikaskumar.bakingapp.pojo.Recipe;
import com.example.udacity.vikaskumar.bakingapp.pojo.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailStepsAdapter extends RecyclerView.Adapter<RecipeDetailStepsAdapter.RecyclerViewHolder>{

    List<Step> mSteps;
    private  String recipeName;

    final private ListItemClickListener lOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(List<Step> stepsOut,int clickedItemIndex,String recipeName);
    }

    public RecipeDetailStepsAdapter(Recipe recipesIn, Context context, ListItemClickListener listener) {
        mSteps = recipesIn.getSteps();
        recipeName = recipesIn.getName();
        lOnClickListener = listener;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.card_view_recipe_steps;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.stepShortDescTv.setText(mSteps.get(position).getId()+". "+ mSteps.get(position).getShortDescription());

    }

    @Override
    public int getItemCount() {

        return mSteps != null ? mSteps.size():0 ;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.steps_short_description_tv) TextView stepShortDescTv;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            lOnClickListener.onListItemClick(mSteps,clickedPosition,recipeName);
        }

    }
}
