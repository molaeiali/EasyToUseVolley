package org.molaei.easyvolley;

import com.android.volley.VolleyError;

public interface EasyVolleyWorks {

    void preExecute(String tag);

    void postExecute(String tag, String jsonToParse);

    void onFailure(String tag, VolleyError error);
}
