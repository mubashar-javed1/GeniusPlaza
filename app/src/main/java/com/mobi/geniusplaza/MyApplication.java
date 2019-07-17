package com.mobi.geniusplaza;

import android.app.Application;

import com.mobi.geniusplaza.di.component.AppComponent;
import com.mobi.geniusplaza.di.component.DaggerAppComponent;
import com.mobi.geniusplaza.di.module.AppModule;
import com.mobi.geniusplaza.di.module.RetrofitModule;

public class MyApplication extends Application {
    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).retrofitModule(new RetrofitModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}