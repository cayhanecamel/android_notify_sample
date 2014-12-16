package jp.cayhanecamel.notifysample;

import android.app.Application;


public class App extends Application {

    private static App sInstance;


    public static App get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        sInstance = this;
    }
}
