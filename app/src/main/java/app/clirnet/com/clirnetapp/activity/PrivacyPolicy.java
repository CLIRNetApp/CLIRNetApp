package app.clirnet.com.clirnetapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;

import app.clirnet.com.clirnetapp.R;
@SuppressLint("SetJavaScriptEnabled")
public class PrivacyPolicy extends AppCompatActivity {

    private static final String LOCAL_RESOURCE = "file:///android_asset/privacypolicy.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        WebView webview = (WebView) findViewById(R.id.webView);

        loadResource(webview, LOCAL_RESOURCE);


    }

    private void loadResource(WebView wv, String resource) {
        wv.loadUrl(resource);
        wv.getSettings().setJavaScriptEnabled(true);
    }

    @Override

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
