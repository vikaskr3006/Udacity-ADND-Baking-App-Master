package com.example.udacity.vikaskumar.bakingapp.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.example.udacity.vikaskumar.bakingapp.R;

import java.util.ArrayList;

public class UpdateBakingService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    static String FROM_ACTIVITY_INGREDIENTS_LIST = String.valueOf(R.string.FROM_ACTIVITY_INGREDIENTS_LIST);

    public UpdateBakingService() {
        super("UpdateBakingService");
    }

    public static void startBakingService(Context context, ArrayList<String> fromActivityIngredientsList) {
        Intent intent = new Intent(context, UpdateBakingService.class);
        intent.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST,fromActivityIngredientsList);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ArrayList<String> fromActivityIngredientsList = intent.getExtras().getStringArrayList(FROM_ACTIVITY_INGREDIENTS_LIST);
            handleActionUpdateBakingWidgets(fromActivityIngredientsList);

        }
    }

    private void handleActionUpdateBakingWidgets(ArrayList<String> fromActivityIngredientsList) {
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST,fromActivityIngredientsList);
        sendBroadcast(intent);
    }
}

