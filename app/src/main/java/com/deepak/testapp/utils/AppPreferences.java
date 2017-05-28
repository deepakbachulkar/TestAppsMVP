package com.deepak.testapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Deepak on 5/27/2017.
 */
public class AppPreferences
{
    private static SharedPreferences preferences;
    private static String TESTAPP ="testapp";
    private static String APP_VERSION = "app_version";

    public static void setAppVersion(Context context, int version)
    {
        preferences = context.getSharedPreferences(TESTAPP, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(APP_VERSION, version);
        editor.commit();
    }

    public static int getVersion(Context context)
    {
        preferences = context.getSharedPreferences(TESTAPP, context.MODE_PRIVATE);
        if(preferences!=null)
            return  preferences.getInt(APP_VERSION, 1);
        else
            return 1;
    }

}
