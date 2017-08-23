package org.molaei.easytousevolley.api;


import android.app.Activity;
import android.content.Context;

import org.molaei.easyvolley.EasyVolleyLoading;
import org.molaei.easyvolley.EasyVolleyManager;

import java.util.HashMap;

public class EasyVolley extends EasyVolleyManager {
    private static EasyVolley instance;

    public static EasyVolley getInstance(Activity activity) {
        if(instance == null){
            instance = new EasyVolley(activity);
        }
        return instance;
    }

    private EasyVolley(Activity activity){
        super(activity);
    }


    @Override
    protected HashMap<String, String> defaultHeaders() {
        return null;
    }

    @Override
    protected EasyVolleyLoading getLoading(Context context) {
        return null;
    }
}
