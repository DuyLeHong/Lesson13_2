package com.example.lesson13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = findViewById(R.id.app_wv);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        webView.setWebViewClient(new MyWebViewClient());

        //webView.loadUrl("https://vnexpress.net/");


//        String unencodedHtml =
//                "<html>\n" +
//                        "  <head>\n" +
//                        "  </head>\n" +
//                        "  <body>\n" +
//                        "    <p>This file was loaded from in-app content</p>\n" +
//                        "    <input type=\"button\" value=\"Say hello\" onClick=\"showAndroidToast('Hello Duy!')\" />\n" +
//                        "\n" +
//                        "    <script type=\"text/javascript\">\n" +
//                        "        function showAndroidToast(toast) {\n" +
//                        "            Android.showToast(toast);\n" +
//                        "        }\n" +
//                        "    </script>\n" +
//                        "  </body>\n" +
//                        "</html>";
//
//        String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(),
//                Base64.NO_PADDING);

        webView.addJavascriptInterface(new WebAppInterface(this), "Android");

        webView.loadUrl("file:///android_asset/untitled.html");

        //webView.loadData(encodedHtml, "text/html", "base64");
    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

            String sUrl = request.getUrl().getHost();
            if (sUrl.contains("vnexpress.net")) {
                // This is my website, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
            startActivity(intent);
            return true;

        }
    }

}