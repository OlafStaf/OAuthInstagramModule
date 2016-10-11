package com.olafstaf.authinstagrammodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.olafstaf.oauthinstagram.InstagramOAuth;
import com.olafstaf.oauthinstagram.model.AccessTokenResponse;

/**
 * Created by oleksandr on 11.10.16.
 */

public class MainActivity extends AppCompatActivity implements InstagramOAuth.OAuthAuthenticationListener {
    private InstagramOAuth instagramOAuth;
    private Button btnSignIn;
    private TextView tvUserName;
    private TextView tvFullName;
    private TextView tvAccessToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setupInstagramOAuth();
        setupViews();

    }


    private void setupInstagramOAuth() {
        instagramOAuth = new InstagramOAuth(this, getString(R.string.instagram_id), getString(R.string.instagram_secret), getString(R.string.callbackurl));
        instagramOAuth.setListener(this);
    }

    private void setupViews() {
        btnSignIn = (Button) findViewById(R.id.btnLogin);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvFullName = (TextView) findViewById(R.id.tvFullName);
        tvAccessToken = (TextView) findViewById(R.id.tvAccessToken);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instagramOAuth.authorize();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        instagramOAuth.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(AccessTokenResponse tokenResponse) {
        tvFullName.setText(tokenResponse.getUser().getFullName());
        tvUserName.setText(tokenResponse.getUser().getUsername());
        tvAccessToken.setText(tokenResponse.getAccessToken());
    }

    @Override
    public void onFail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}
