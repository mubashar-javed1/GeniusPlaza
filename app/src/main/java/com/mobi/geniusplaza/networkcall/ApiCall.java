package com.mobi.geniusplaza.networkcall;

import com.mobi.geniusplaza.data.User;
import com.mobi.geniusplaza.data.UserResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiCall {
    @GET(ApiConfig.USER)
    Observable<UserResponse> getUser();

    @POST(ApiConfig.USER)
    Observable<User> createUser(@Body User user);
}