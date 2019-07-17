package com.mobi.geniusplaza.networkcall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobi.geniusplaza.data.UserResponse;

import static com.mobi.geniusplaza.networkcall.Status.ERROR;
import static com.mobi.geniusplaza.networkcall.Status.LOADING;
import static com.mobi.geniusplaza.networkcall.Status.SUCCESS;


public class ResponseGetUser {

    public final Status status;

    @Nullable
    public final UserResponse data;

    @Nullable
    public final Throwable error;

    private ResponseGetUser(Status status, @Nullable UserResponse data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static ResponseGetUser loading() {
        return new ResponseGetUser(LOADING, null, null);
    }

    public static ResponseGetUser success(@NonNull UserResponse data) {
        return new ResponseGetUser(SUCCESS, data, null);
    }

    public static ResponseGetUser responseError(@NonNull Throwable error) {
        return new ResponseGetUser(ERROR, null, error);
    }
}