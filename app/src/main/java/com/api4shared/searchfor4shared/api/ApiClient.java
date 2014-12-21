package com.api4shared.searchfor4shared.api;

import retrofit.RestAdapter;

public class ApiClient {

    private static I4SharedSearchApi searchApi4Shared;

    public static final String SEARCH_API_4SHARED_ENDPOINT = "https://api.4shared.com/v0";

    public static I4SharedSearchApi getSearchApi() {
        if (searchApi4Shared == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(SEARCH_API_4SHARED_ENDPOINT)
                    .build();
            searchApi4Shared = restAdapter.create(I4SharedSearchApi.class);
        }
        return searchApi4Shared;
    }

}
