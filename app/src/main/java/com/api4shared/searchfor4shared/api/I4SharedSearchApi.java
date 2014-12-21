package com.api4shared.searchfor4shared.api;

import com.api4shared.searchfor4shared.models.FilesHolder;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface I4SharedSearchApi  {

    public static final String SEARCH_API_4SHARED_ENDPOINT = "https://api.4shared.com/v0/files.json";

    @GET("/files.json")
    void searchFiles(@Query("oauth_consumer_key") String consumerKey,
                     @Query("limit") int limit,
                     @Query("offset") int offset,
                     @Query("query") String query,
                     Callback<FilesHolder> callback);
}
