# android-OAuth-Instagram
Simple Android OAuth Instagram implementation.

##Usage
Register your application on https://www.instagram.com/developer/
```gradle
dependencies {
compile project(path: ':oauthinstagram')
}
```

```java
public class MainActivity extends AppCompatActivity implements InstagramOAuth.OAuthAuthenticationListener {
    private InstagramOAuth instagramOAuth;
    private Button btnSignIn;

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
         Log.i("AccessToken",tokenResponse.getAccessToken());
    }

    @Override
    public void onFail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}
```
<img src="/art/S1.jpg" width="200">
<img src="/art/S2.jpg" width="200">
<img src="/art/S3.jpg" width="200">
<img src="/art/S4.jpg" width="200">
