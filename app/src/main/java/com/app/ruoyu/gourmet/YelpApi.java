package com.app.ruoyu.gourmet;

import android.util.Log;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class YelpApi {
    private static final String API_HOST = "api.yelp.com";
    private static final String SEARCH_PATH = "/v2/search";

    private static final String CONSUMER_KEY = "G-rl4U-x7_LLkI8QfYSMvg";
    private static final String CONSUMER_SECRET = "FWSy8OrBysrhzWr8dzIa6VIURAY";
    private static final String TOKEN = "aIH-XvJwyJEM8P6bGIl-RsDGvvcNMbhH";
    private static final String TOKEN_SECRET = "_Pb8VtEtq3VQikSN9URYj6freSI";

    private OAuthService service;
    private Token accessToken;

    /**
     * Setup the Yelp API OAuth credentials.
     */
    public YelpApi() {
        this.service = new
                ServiceBuilder().provider(TwoStepOAuth.class).apiKey(CONSUMER_KEY)
                .apiSecret(CONSUMER_SECRET).build();
        this.accessToken = new Token(TOKEN, TOKEN_SECRET);
    }

    /**
     * Fire a search request to Yelp API.
     */
    public String searchForBusinessesByLocation(String term, String location, int searchLimit) {
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + API_HOST + SEARCH_PATH);
        request.addQuerystringParameter("term", term);
        request.addQuerystringParameter("location", location);
        request.addQuerystringParameter("limit", String.valueOf(searchLimit));
        this.service.signRequest(this.accessToken, request);
        Response response = request.send();
        Log.i("message", response.getBody());
        return response.getBody();
    }
}