package com.example.tripti;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface getAPI {

    @GET
    Call<NewsModel> gNews(@Url String url);
}
