package org.molaei.easytousevolley.activities;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.molaei.easytousevolley.R;
import org.molaei.easytousevolley.api.EasyVolley;
import org.molaei.easyvolley.EasyVolleyWorks;

import java.io.File;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements EasyVolleyWorks {

    private TextView textView;
    private final String googleTag = "google";
    private final String molaeiTag = "molaeiTag";
    private final String bingTag = "bing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.activity_main_tv);

        EasyVolley.getInstance(this).GET(googleTag, this, "http://google.com", this, false);
        EasyVolley.getInstance(this).GET(bingTag, this, "http://bing.com", this, false);
        HashMap<String,File> files = new HashMap<>();
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/Camera/1.jpg");
        files.put("photo",file);
        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("msg","xyz");
        EasyVolley.getInstance(this).MultiPart(molaeiTag, this, "http://molaei.org/testapi2.php", parameters, files, this, false);
    }

    @Override
    public void preExecute(String tag) {
        textView.setText(String.format("%s\nLoading %s\n", textView.getText(), tag));
    }

    @Override
    public void postExecute(String tag, String response) {
        switch (tag) {
            case googleTag:
                textView.setText(String.format("%s\n%s-> %s\n", textView.getText(), tag, response.substring(0, 300)));
                break;
            case bingTag:
                textView.setText(String.format("%s\n%s-> %s\n", textView.getText(), tag, response.substring(0, 300)));
                break;
            case molaeiTag:
                textView.setText(String.format("%s\n%s-> %s\n", textView.getText(), tag, response));
                break;
            default:
                textView.setText(String.format("%s%s", textView.getText(), tag));
                break;
        }
    }

    @Override
    public void onFailure(String tag, VolleyError error) {
        textView.setText(String.format("%s\nError %s\n%s\n%s\n", textView.getText(), tag, error.toString(), error.networkResponse.statusCode));
    }
}
