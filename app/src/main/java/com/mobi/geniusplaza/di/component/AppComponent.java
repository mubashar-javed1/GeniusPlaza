package com.mobi.geniusplaza.di.component;


import com.mobi.geniusplaza.di.module.AppModule;
import com.mobi.geniusplaza.di.module.RetrofitModule;
import com.mobi.geniusplaza.ui.createuseractivity.AddUserActivity;
import com.mobi.geniusplaza.ui.mainactivity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, RetrofitModule.class})
@Singleton
public interface AppComponent {
    void injectMainActivity(MainActivity mainActivity);
    void injectUserActivity(AddUserActivity mainActivity);
}
