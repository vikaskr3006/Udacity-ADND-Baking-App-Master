package com.example.udacity.vikaskumar.bakingapp.ui;


import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udacity.vikaskumar.bakingapp.R;
import com.example.udacity.vikaskumar.bakingapp.pojo.Recipe;
import com.example.udacity.vikaskumar.bakingapp.pojo.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailFragment extends Fragment {

    private SimpleExoPlayer player;
    private BandwidthMeter bandwidthMeter;
    private ArrayList<Step> steps = new ArrayList<>();
    private int selectedIndex;
    private Handler mainHandler;
    Recipe recipe;
    String recipeName;

    @BindView(R.id.exo_player_view) SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R.id.recipe_step_detail_text) TextView recipeStepDetailTv;
    @BindView(R.id.step_detail_iv) ImageView thumbImage;
    @BindView(R.id.previousStep) Button mPrevStepButton;
    @BindView(R.id.nextStep) Button mNextStepButton;

    @BindString(R.string.SELECTED_RECIPES) String SELECTED_RECIPES;
    @BindString(R.string.SELECTED_STEPS) String SELECTED_STEPS;
    @BindString(R.string.SELECTED_INDEX) String SELECTED_INDEX;
    @BindString(R.string.RECIPE_NAME) String RECIPE_NAME;

    // Mandatory constructor for instantiating the fragment
    public RecipeStepDetailFragment() {
    }

    private ListItemClickListener itemClickListener;

    public interface ListItemClickListener {
        void onListItemClick(List<Step> allSteps, int Index, String recipeName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        ButterKnife.bind(this, rootView);

        mainHandler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();

        itemClickListener =(RecipeDetailActivity)getActivity();


        if(savedInstanceState != null) {
            steps = savedInstanceState.getParcelableArrayList(SELECTED_STEPS);
            selectedIndex = savedInstanceState.getInt(SELECTED_INDEX);
            recipeName = savedInstanceState.getString(RECIPE_NAME);
        }
        else {
            steps =getArguments().getParcelableArrayList(SELECTED_STEPS);
            if (steps!=null) {
                steps =getArguments().getParcelableArrayList(SELECTED_STEPS);
                selectedIndex=getArguments().getInt(SELECTED_INDEX);
                recipeName=getArguments().getString(RECIPE_NAME);
            }
            else {
                recipe =getArguments().getParcelable(SELECTED_RECIPES);
                //casting List to ArrayList
                steps=(ArrayList<Step>)recipe.getSteps();
                selectedIndex=0;
            }

        }

        recipeStepDetailTv.setText(steps.get(selectedIndex).getDescription());
        recipeStepDetailTv.setVisibility(View.VISIBLE);

        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        String videoURL = steps.get(selectedIndex).getVideoURL();

        if (rootView.findViewWithTag(getString(R.string.tag_sw600dp_port_recipe_step_detail))!=null) {
            recipeName=((RecipeDetailActivity) getActivity()).recipeName;
            ((RecipeDetailActivity) getActivity()).getSupportActionBar().setTitle(recipeName);
        }

        String imageUrl=steps.get(selectedIndex).getThumbnailURL();
        if (imageUrl!="") {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            Picasso.with(getContext()).load(builtUri).into(thumbImage);
        }

        if (!videoURL.isEmpty()) {

            initializePlayer(Uri.parse(steps.get(selectedIndex).getVideoURL()));

            if (rootView.findViewWithTag(getString(R.string.tag_sw600dp_land_recipe_step_detail))!=null) {
                getActivity().findViewById(R.id.recipe_detail_fragment_container_2)
                        .setLayoutParams(new LinearLayout.LayoutParams(-1,-2));
                simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
            }
            else if (isInLandscapeMode(getContext())){
                recipeStepDetailTv.setVisibility(View.GONE);
            }
        }
        else {
            player=null;
            simpleExoPlayerView.setForeground(ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_off_white_36dp));
            simpleExoPlayerView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
        }

        mPrevStepButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (steps.get(selectedIndex).getId() > 0) {
                    if (player!=null){
                        player.stop();
                    }
                    itemClickListener.onListItemClick(steps,steps.get(selectedIndex).getId() - 1,recipeName);
                }
                else {
                    Toast.makeText(getActivity(),getString(R.string.first_step_msg), Toast.LENGTH_SHORT).show();

                }
            }});

        mNextStepButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                int lastIndex = steps.size()-1;
                if (steps.get(selectedIndex).getId() < steps.get(lastIndex).getId()) {
                    if (player!=null){
                        player.stop();
                    }
                    itemClickListener.onListItemClick(steps,steps.get(selectedIndex).getId() + 1,recipeName);
                }
                else {
                    Toast.makeText(getContext(),getString(R.string.last_step_msg), Toast.LENGTH_SHORT).show();

                }
            }});

        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (player == null) {
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();

            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(player);

            String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(),
                    userAgent), new DefaultExtractorsFactory(), null, null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(SELECTED_STEPS,steps);
        currentState.putInt(SELECTED_INDEX,selectedIndex);
        currentState.putString(RECIPE_NAME,recipeName);
    }

    public boolean isInLandscapeMode( Context context ) {
        return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player!=null) {
            player.stop();
            player.release();
            player=null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }
}
