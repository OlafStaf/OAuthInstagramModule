package com.olafstaf.oauthinstagram.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 11.10.16.
 */

public class AccessTokenResponse {

    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("user")
    @Expose
    private User user;

    /**
     * @return The accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken The access_token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return The user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUser(User user) {
        this.user = user;
    }

}
