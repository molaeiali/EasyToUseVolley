package org.molaei.easyvolley;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class EasyVolleyManager {
    private RequestQueue queue;

    protected EasyVolleyManager(Context context) {
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
    }

    protected abstract HashMap<String, String> defaultHeaders();

    protected abstract EasyVolleyLoading getLoading(Context context);

    public void GET(final String tag, final Context context, String url, final EasyVolleyWorks easyVolleyWorks, final boolean hasLogin) {
        GET(tag, context, url, easyVolleyWorks, hasLogin, defaultHeaders());
    }

    @SuppressWarnings("WeakerAccess")
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
                return headers == null ? new HashMap<String, String>() : headers;
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
        POST(tag, context, url, parameters, easyVolleyWorks, hasLogin, defaultHeaders());
    }

    @SuppressWarnings("WeakerAccess")
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
                return headers == null ? new HashMap<String, String>() : headers;
            }
        };
        Log.i("EasyVolleyPreExecPOST", tag);
        easyVolleyWorks.preExecute(tag);
        if (hasLogin && loadingInterface != null) {
            loadingInterface.start();
        }
        queue.add(stringRequest);
    }

    private final String twoHyphens = "--";
    private final String lineEnd = "\r\n";
    private final String boundary = "apiclient-" + System.currentTimeMillis();
    private final String mimeType = "multipart/form-data;boundary=" + boundary;

    public void MultiPart(final String tag, final Context context, String url, final HashMap<String, String> parameters, final HashMap<String, File> files, final EasyVolleyWorks easyVolleyWorks, final boolean hasLogin) {
        MultiPart(tag, context, url, parameters, files, easyVolleyWorks, hasLogin, defaultHeaders());
    }

    @SuppressWarnings("WeakerAccess")
    public void MultiPart(final String tag, final Context context, String url, final HashMap<String, String> parameters, final HashMap<String, File> files, final EasyVolleyWorks easyVolleyWorks, final boolean hasLogin, final HashMap<String, String> headers) {
        final EasyVolleyLoading loadingInterface = getLoading(context);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        try {
            if (files != null) {
                for (Map.Entry<String, File> file : files.entrySet()) {
                    FileInputStream fis;
                    try {
                        fis = new FileInputStream(file.getValue());
                        ByteArrayOutputStream bosForFile = new ByteArrayOutputStream();
                        byte[] buf = new byte[1024];
                        try {
                            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                                bosForFile.write(buf, 0, readNum);
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        byte[] bytes = bosForFile.toByteArray();
                        buildPart(dos, bytes, file.getValue().getName(), file.getKey());

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
            if (parameters != null) {
                for (Map.Entry<String, String> parameter : parameters.entrySet()) {
                    buildStringPart(dos, parameter.getValue().getBytes(), parameter.getKey());
                }
            }
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            byte[] multipartBody = bos.toByteArray();

            EasyVolleyMultipartRequest multipartRequest = new EasyVolleyMultipartRequest(url, mimeType, multipartBody, new Response.Listener<NetworkResponse>() {
                @Override
                public void onResponse(NetworkResponse response) {
                    if (hasLogin && loadingInterface != null) {
                        loadingInterface.stop();
                    }
                    Log.i("EasyVolleyPostExecMULTI", tag);
                    easyVolleyWorks.postExecute(tag, new String(response.data));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (hasLogin && loadingInterface != null) {
                        loadingInterface.stop();
                    }
                    Log.i("EasyVolleyFailedMULTI", tag);
                    easyVolleyWorks.onFailure(tag, error);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return headers == null ? super.getHeaders() : headers;
                }
            };
            Log.i("EasyVolleyPreExecMULTI", tag);
            easyVolleyWorks.preExecute(tag);
            if (hasLogin && loadingInterface != null) {
                loadingInterface.start();
            }
            queue.add(multipartRequest);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void buildStringPart(DataOutputStream dataOutputStream, byte[] fileData, String partName) throws IOException {
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\""
                + partName + "\"" + lineEnd);
        dataOutputStream.writeBytes("Content-Type: plain/text" + lineEnd);
        dataOutputStream.writeBytes(lineEnd);

        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(fileData);
        int bytesAvailable = fileInputStream.available();

        int maxBufferSize = 1024 * 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            dataOutputStream.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        dataOutputStream.writeBytes(lineEnd);
    }

    private void buildPart(DataOutputStream dataOutputStream, byte[] fileData, String fileName, String partName) throws IOException {
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=" + partName + "; filename=\""
                + fileName + "\"" + lineEnd);
        dataOutputStream.writeBytes(lineEnd);

        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(fileData);
        int bytesAvailable = fileInputStream.available();

        int maxBufferSize = 1024 * 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            dataOutputStream.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        dataOutputStream.writeBytes(lineEnd);
    }
}
