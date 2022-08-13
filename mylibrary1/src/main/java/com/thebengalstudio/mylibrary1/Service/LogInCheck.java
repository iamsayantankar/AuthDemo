package com.thebengalstudio.mylibrary1.Service;

import static com.thebengalstudio.mylibrary1.Service.SharePref.LoadSharePref;

import android.app.Activity;
import android.content.Context;

public class LogInCheck {

    private static Context context01;
    private static Activity activity01;

    public static boolean LogInCheck(Context context){
        if(((LoadSharePref(context, "auth_uid").isEmpty()) || (LoadSharePref(context, "auth_uid").equals("null")))){
            return false;
        }else{
            return true;
        }

    }



}
