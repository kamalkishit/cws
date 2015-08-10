package com.contentws.cws;

import android.app.Application;
import android.content.Context;

/**
 * Created by Kamal on 7/7/15.
 */
public class ApplicationState extends Application{

    private static Context context;

    public void onCreate(){
        super.onCreate();
        ApplicationState.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ApplicationState.context;
    }
}
