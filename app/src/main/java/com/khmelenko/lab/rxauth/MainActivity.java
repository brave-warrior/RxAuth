package com.khmelenko.lab.rxauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button requestTokenBtn = (Button) findViewById(R.id.request_token);
        assert requestTokenBtn != null;
        requestTokenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRequestToken();
            }
        });


    }

    private void startRequestToken() {
        final OAuth10aService service = new ServiceBuilder()
                .apiKey("your_api_key")
                .apiSecret("your_api_secret")
                .build(TwitterApi.instance());

        final OAuth1RequestToken requestToken = service.getRequestToken();

//        String authUrl = service.getAuthorizationUrl(requestToken);
//
        final OAuth1AccessToken accessToken = service.getAccessToken(requestToken,
                "verifier you got from the user/callback");

        final OAuthRequest request = new OAuthRequest(Verb.GET,
                "https://api.twitter.com/1.1/account/verify_credentials.json", service);
        service.signRequest(accessToken, request); // the access token from step 4
        final Response response = request.send();
        System.out.println(response.getBody());
    }
}
