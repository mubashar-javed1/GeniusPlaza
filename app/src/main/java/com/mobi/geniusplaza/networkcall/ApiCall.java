package com.mobi.geniusplaza.networkcall;

import com.mobi.geniusplaza.data.UserResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiCall {
    @GET(ApiConfig.USER)
    Observable<UserResponse> getUser();
}