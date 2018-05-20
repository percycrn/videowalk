package com.zego.videotalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkView;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        XWalkView xWalkView = findViewById(R.id.xWalkWebView);
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
        String url = "https://www.baidu.com";
        xWalkView.load(url, null);
    }

    public void jump(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}