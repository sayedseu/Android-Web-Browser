package com.example.my_web_view;


import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MyWebChromeClient extends WebChromeClient {

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

        return super.onJsAlert(view,url,message,result);
    }
}
