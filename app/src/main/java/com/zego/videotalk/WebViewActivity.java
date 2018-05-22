package com.zego.videotalk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.ValueCallback;

import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

public class WebViewActivity extends AppCompatActivity {

    private ValueCallback mUploadMessage;
    private final int FILE_SELECTED = 10000;
    private XWalkView mXWalkView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mXWalkView != null) {
            mXWalkView.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == FILE_SELECTED) {
            if (null == mUploadMessage) return;
            Uri result = data == null || resultCode != RESULT_OK ? null
                    : data.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mXWalkView = findViewById(R.id.xWalkWebView);
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
        String url = "http://101.132.123.133:8081/iTutor/";
        mXWalkView.setUIClient(new XWalkUIClient(mXWalkView) {
            @Override
            public void openFileChooser(XWalkView view, ValueCallback<Uri> uploadFile, String acceptType, String capture) {
                super.openFileChooser(view, uploadFile, acceptType, capture);
                if (mUploadMessage != null) return;
                mUploadMessage = uploadFile;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                WebViewActivity.this.startActivityForResult(Intent.createChooser(i, "选择图片"), FILE_SELECTED);
            }
        });

        mXWalkView.load(url, null);
    }

    public void jump(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}