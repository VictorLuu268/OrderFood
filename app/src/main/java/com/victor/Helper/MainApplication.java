package com.victor.Helper;

import android.app.Application;
import android.content.Context;

/**
 * Created by Victor on 18/07/2017.
 */

public class MainApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }
}
