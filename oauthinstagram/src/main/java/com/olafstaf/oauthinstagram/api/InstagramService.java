package com.olafstaf.oauthinstagram.api;

import com.olafstaf.oauthinstagram.model.AccessTokenResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by oleksandr on 11.10.16.
 */

public interface InstagramService {

    @FormUrlEncoded
    @POST("oauth/access_token")
    Observable<AccessTokenResponse> getAccessToken(@Field("client_id") String clientId,
                                                   @Field("client_secret") String clientSecret,
                                                   @Field("grant_type") String grantType,
                                                   @Field("redirect_uri") String redirectUri,
                                                   @Field("code") String code);
}
