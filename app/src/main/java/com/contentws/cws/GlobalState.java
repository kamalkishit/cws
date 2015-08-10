package com.contentws.cws;

import android.app.Application;

/**
 * Created by Kamal on 7/7/15.
 */
public class GlobalState {
    private static GlobalState globalState = null;
    private Application application;

    private GlobalState(Application application) {
        this.application = application;
    }

    public static GlobalState createInstance(Application application) {
        if (globalState == null) {
            globalState = new GlobalState(application);
        }

        return globalState;
    }

    public static GlobalState getInstance() {
        return globalState;
    }

    public Application getApplication() {
        return application;
    }
}
