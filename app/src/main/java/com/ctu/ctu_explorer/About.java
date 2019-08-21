package com.ctu.ctu_explorer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class About extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        WebView webView =(WebView)findViewById(R.id.wv);
        //String myPdfUrl = "https://drive.google.com/open?id=1pNRRSaArBqMU2fSK640gFGmjIg14fPal";
        String url = "https://www.ctu.edu.vn/gioi-thieu.html";// + myPdfUrl;

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
        // Tùy biến WebViewClient để điều khiển các sự kiện trên WebView
    }
}
