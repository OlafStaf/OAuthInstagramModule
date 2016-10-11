package com.olafstaf.oauthinstagram;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.olafstaf.oauthinstagram.api.RestClient;
import com.olafstaf.oauthinstagram.model.AccessTokenResponse;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class InstagramOAuth {

    private OAuthAuthenticationListener listener;
    private ProgressDialog progress;
    private String authUrl;
    private Activity activity;

    private String clientId;
    private String clientSecret;

    static String callbackUrl;
    private static final String AUTH_URL = "https://api.instagram.com/oauth/authorize/";


    public InstagramOAuth(@NonNull Activity context, @NonNull String clientId, @NonNull String clientSecret,
                          @NonNull String callbackUrl) {

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        activity = context;
        InstagramOAuth.callbackUrl = callbackUrl;
        authUrl = AUTH_URL + "?client_id=" + clientId + "&redirect_uri="
                + InstagramOAuth.callbackUrl + "&response_type=code";
        progress = new ProgressDialog(context);
        progress.setCancelable(false);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == InstagramActivity.INSTAGRAM_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            getAccessToken(data);
        }
    }

    private void getAccessToken(Intent data) {
        String code = data.getStringExtra(InstagramActivity.INSTAGRAM_AUTH_TOKEN_KEY);
        progress.show();
        RestClient.getInstance().getInstagramApiService()
                .getAccessToken(clientId, clientSecret, "authorization_code", callbackUrl, code)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<AccessTokenResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        progress.dismiss();
                        listener.onFail(e.getMessage());
                    }

                    @Override
                    public void onNext(AccessTokenResponse accessTokenResponse) {
                        progress.dismiss();
                        listener.onSuccess(accessTokenResponse);
                    }
                });
    }

    public void setListener(OAuthAuthenticationListener listener) {
        this.listener = listener;
    }


    public void authorize() {
        InstagramActivity.startInstagramSignIn(activity, authUrl);
    }


    public interface OAuthAuthenticationListener {
        void onSuccess(AccessTokenResponse tokenResponse);

        void onFail(String error);
    }
}