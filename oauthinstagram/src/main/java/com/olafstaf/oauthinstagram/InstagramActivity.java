package com.olafstaf.oauthinstagram;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by oleksandr on 11.10.16.
 */

public class InstagramActivity extends AppCompatActivity {
    public static final int INSTAGRAM_REQUEST_CODE = 2312;
    public static final String INSTAGRAM_AUTH_TOKEN_KEY = "instagram_auth_token_key";
    public static final String INSTAGRAM_AUTH_URL = "instagram_auth_url";
    private String url;
    private ProgressDialog progressDialog;
    private WebView webView;


    public static void startInstagramSignIn(Activity activity, String url) {
        Intent startIntent = new Intent(activity, InstagramActivity.class);
        startIntent.putExtra(INSTAGRAM_AUTH_URL, url);
        activity.startActivityForResult(startIntent, INSTAGRAM_REQUEST_CODE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.yummi.oauthinstagram.R.layout.main);
        url = getIntent().getStringExtra(INSTAGRAM_AUTH_URL);
        webView = (WebView) findViewById(R.id.webview);
        progressDialog = new ProgressDialog(this);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setMessage(getString(R.string.loading));
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(this);
        }
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();
        setupWebView();
    }


    private void setupWebView() {
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new OAuthWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    private class OAuthWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(InstagramOAuth.callbackUrl)) {
                String urls[] = url.split("=");
                Intent intent = new Intent();
                intent.putExtra(INSTAGRAM_AUTH_TOKEN_KEY, urls[1]);
                InstagramActivity.this.setResult(RESULT_OK, intent);
                InstagramActivity.this.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {

            super.onReceivedError(view, errorCode, description, failingUrl);
            InstagramActivity.this.setResult(RESULT_CANCELED);
            Toast.makeText(InstagramActivity.this, description, Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (url.equalsIgnoreCase(InstagramActivity.this.url))
                progressDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressDialog.dismiss();
        }

    }
}
