package com.mobi.geniusplaza.networkcall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobi.geniusplaza.data.User;

import static com.mobi.geniusplaza.networkcall.Status.ERROR;
import static com.mobi.geniusplaza.networkcall.Status.LOADING;
import static com.mobi.geniusplaza.networkcall.Status.SUCCESS;


public class ResponseCreateUser {

    public final Status status;

    @Nullable
    public final User user;

    @Nullable
    public final Throwable error;

    private ResponseCreateUser(Status status, @Nullable User user, @Nullable Throwable error) {
        this.status = status;
        this.user = user;
        this.error = error;
    }

    public static ResponseCreateUser loading() {
        return new ResponseCreateUser(LOADING, null, null);
    }

    public static ResponseCreateUser success(@NonNull User user) {
        return new ResponseCreateUser(SUCCESS, user, null);
    }

    public static ResponseCreateUser responseError(@NonNull Throwable error) {
        return new ResponseCreateUser(ERROR, null, error);
    }
}