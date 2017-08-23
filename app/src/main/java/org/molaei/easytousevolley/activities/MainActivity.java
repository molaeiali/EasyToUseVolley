package org.molaei.easytousevolley.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.molaei.easytousevolley.R;
import org.molaei.easytousevolley.api.EasyVolley;
import org.molaei.easyvolley.EasyVolleyWorks;

public class MainActivity extends AppCompatActivity implements EasyVolleyWorks {

    private TextView textView;
    private final String googleTag = "google";
    private final String bingTag = "bing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.activity_main_tv);

        EasyVolley.getInstance(this).GET(googleTag, this, "http://google.com", this, false);
        EasyVolley.getInstance(this).GET(bingTag, this, "http://bing.com", this, false);
    }

    @Override
    public void preExecute(String tag) {
        textView.setText(String.format("%s\nLoading %s\n", textView.getText(), tag));
    }

    @Override
    public void postExecute(String tag, String jsonToParse) {
        switch (tag) {
            case googleTag:
                textView.setText(String.format("%s\n%s-> %s\n", textView.getText(), tag, jsonToParse.substring(0, 300)));
                break;
            case bingTag:
                textView.setText(String.format("%s\n%s-> %s\n", textView.getText(), tag, jsonToParse.substring(0, 300)));
                break;
            default:
                textView.setText(String.format("%s%s", textView.getText(), tag));
                break;
        }
    }

    @Override
    public void onFailure(String tag, VolleyError error) {
        textView.setText(String.format("%s\nError %s\n", textView.getText(), tag));
    }
}
