package com.contentws.cws.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.view.View;

import com.contentws.cws.Config;

import com.contentws.cws.ContentNavigator;
import com.contentws.cws.GlobalState;
import com.contentws.cws.R;

public class MainActivity extends Activity {

    private final String FILENAME = "com.contentws.contentws.Activity.MainActivity";

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int imageWidth = metrics.widthPixels;
        int imageHeight = (imageWidth*2)/3;

        Config.IMAGE_WIDTH = imageWidth;
        Config.IMAGE_HEIGHT = imageHeight;
        GlobalState.createInstance(getApplication());

        ContentNavigator contentNavigator = ContentNavigator.getInstance();
    }

    public void load(View view) {
        Intent intent = new Intent(this, ContentActivity.class);
        startActivity(intent);
    }


}
