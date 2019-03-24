package com.example.my_web_view;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;


public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL="https://backpackbang.com/";

    private WebView webView;
    private NumberProgressBar numberProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webViewID);
        numberProgressBar = findViewById(R.id.progressBar);
        numberProgressBar.setVisibility(View.VISIBLE);
        setWebSettings();
        setWebViewClient();
        setWebChromeClient();
        setWebViewDownloadListener();

        if (savedInstanceState==null){
            webView.clearCache(true);
            webView.clearHistory();
            webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.setScrollbarFadingEnabled(true);
            webView.loadUrl(BASE_URL);

        }

    }


    public void setWebSettings(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
    }

    public void setWebViewClient(){

        webView.setWebViewClient(new MyWebClient(this) {
                                     @Override
                                     public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {

                                         if (!detail.didCrash()) {

                                             Log.e("MY_APP_TAG", "System killed the WebView rendering process " +
                                                     "to reclaim memory. Recreating...");

                                             if (webView != null) {
                                                 ViewGroup webViewContainer = findViewById(R.id.webViewID);
                                                 webViewContainer.removeView(webView);
                                                 webView.destroy();
                                                 webView = null;
                                             }

                                             return true;
                                         }


                                         return super.onRenderProcessGone(view, detail);
                                     }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                numberProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
               numberProgressBar.setVisibility(View.GONE);
            }
        });



    }


    public void setWebChromeClient(){

        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                numberProgressBar.setProgress(newProgress);
            }

        });
    }

    private void setWebViewDownloadListener() {
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode==KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        webView.restoreState(savedInstanceState);
    }


}
