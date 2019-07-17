package com.mobi.geniusplaza.repository;



import com.mobi.geniusplaza.data.UserResponse;
import com.mobi.geniusplaza.networkcall.ApiCall;

import io.reactivex.Observable;

public class Repository {
    private ApiCall apiCall;

    public Repository(ApiCall apiCall) {
        this.apiCall = apiCall;
    }

    public Observable<UserResponse> getUsers() {
        return apiCall.getUser();
    }

}