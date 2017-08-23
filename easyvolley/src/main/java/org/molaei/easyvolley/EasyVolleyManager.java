package org.molaei.easyvolley;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.util.HashMap;
import java.util.Map;

public abstract class EasyVolleyManager {
    private RequestQueue queue;

    protected EasyVolleyManager(Context context) {
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
    }

    protected abstract HashMap<String,String> defaultHeaders();

    protected abstract EasyVolleyLoading getLoading(Context context);

    public void GET(final String tag, final Context context, String url, final EasyVolleyWorks easyVolleyWorks, final boolean hasLogin) {
        final EasyVolleyLoading loadingInterface = getLoading(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (hasLogin && loadingInterface != null) {
                            loadingInterface.stop();
                        }
                        Log.i("EasyVolleyPostExecGET", tag);
                        easyVolleyWorks.postExecute(tag, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (hasLogin && loadingInterface != null) {
                    loadingInterface.stop();
                }
                Log.i("EasyVolleyFailedGET", tag);
                easyVolleyWorks.onFailure(tag, error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return defaultHeaders() == null ? new HashMap<String, String>() : defaultHeaders();
            }
        };
        Log.i("EasyVolleyPreExecGET", tag);
        easyVolleyWorks.preExecute(tag);
        if (hasLogin && loadingInterface != null) {
            loadingInterface.start();
        }
        queue.add(stringRequest);
    }

    public void GET(final String tag, final Context context, String url, final EasyVolleyWorks easyVolleyWorks, final boolean hasLogin, final HashMap<String, String> headers) {
        final EasyVolleyLoading loadingInterface = getLoading(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (hasLogin && loadingInterface != null) {
                            loadingInterface.stop();
                        }
                        Log.i("EasyVolleyPostExecGET", tag);
                        easyVolleyWorks.postExecute(tag, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (hasLogin && loadingInterface != null) {
                    loadingInterface.stop();
                }
                Log.i("EasyVolleyFailedGET", tag);
                easyVolleyWorks.onFailure(tag, error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers2 = new HashMap<>();
                if (headers != null) {
                    headers2 = headers;
                }
                return headers2;
            }
        };
        Log.i("EasyVolleyPreExecGET", tag);
        easyVolleyWorks.preExecute(tag);
        if (hasLogin && loadingInterface != null) {
            loadingInterface.start();
        }
        queue.add(stringRequest);
    }

    public void POST(final String tag, final Context context, String url, final HashMap<String, String> parameters, final EasyVolleyWorks easyVolleyWorks, final boolean hasLogin) {
        final EasyVolleyLoading loadingInterface = getLoading(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (hasLogin && loadingInterface != null) {
                            loadingInterface.stop();
                        }
                        Log.i("EasyVolleyPostExecPOST", tag);
                        easyVolleyWorks.postExecute(tag, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (hasLogin && loadingInterface != null) {
                    loadingInterface.stop();
                }
                Log.i("EasyVolleyFailedPOST", tag);
                easyVolleyWorks.onFailure(tag, error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return parameters == null ? new HashMap<String, String>() : parameters;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return defaultHeaders() == null ? new HashMap<String, String>() : defaultHeaders();
            }
        };
        Log.i("EasyVolleyPreExecPOST", tag);
        easyVolleyWorks.preExecute(tag);
        if (hasLogin && loadingInterface != null) {
            loadingInterface.start();
        }
        queue.add(stringRequest);
    }

    public void POST(final String tag, final Context context, String url, final HashMap<String, String> parameters, final EasyVolleyWorks easyVolleyWorks, final boolean hasLogin, final HashMap<String, String> headers) {
        final EasyVolleyLoading loadingInterface = getLoading(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (hasLogin && loadingInterface != null) {
                            loadingInterface.stop();
                        }
                        Log.i("EasyVolleyPostExecPOST", tag);
                        easyVolleyWorks.postExecute(tag, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (hasLogin && loadingInterface != null) {
                    loadingInterface.stop();
                }
                Log.i("EasyVolleyFailedPOST", tag);
                easyVolleyWorks.onFailure(tag, error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                if (parameters != null) {
                    params = parameters;
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers2 = new HashMap<>();
                if (headers != null) {
                    headers2 = headers;
                }
                return headers2;
            }
        };
        Log.i("EasyVolleyPreExecPOST", tag);
        easyVolleyWorks.preExecute(tag);
        if (hasLogin && loadingInterface != null) {
            loadingInterface.start();
        }
        queue.add(stringRequest);
    }

}
