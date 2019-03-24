package com.example.my_web_view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.net.MalformedURLException;
import java.net.URL;

public class MyWebClient extends WebViewClient {

    private Context mContext;

    public MyWebClient(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

            URL nextUrl;
            try {
                nextUrl = new URL(request.getUrl().toString());
            }catch (MalformedURLException e){
                nextUrl = null;
            }


            if ((nextUrl!=null )&& nextUrl.getHost().equals("backpackbang.com")) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(request.getUrl().toString()));
            mContext.startActivity(intent);
            return true;

    }


}
