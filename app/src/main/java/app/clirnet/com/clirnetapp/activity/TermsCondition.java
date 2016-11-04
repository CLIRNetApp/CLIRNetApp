package app.clirnet.com.clirnetapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;

import app.clirnet.com.clirnetapp.R;

public class TermsCondition extends AppCompatActivity {


    private static final String LOCAL_RESOURCE = "file:///android_asset/termscondition.html";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_condition);
        WebView wv = (WebView) findViewById(R.id.browser1);
        loadResource(wv);
    }

    private void loadResource(WebView wv) {
        wv.loadUrl(TermsCondition.LOCAL_RESOURCE);
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