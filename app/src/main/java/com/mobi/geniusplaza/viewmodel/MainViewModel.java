package com.mobi.geniusplaza.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.mobi.geniusplaza.networkcall.ResponseGetUser;
import com.mobi.geniusplaza.repository.Repository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ResponseGetUser> responseLiveData = new MutableLiveData<>();

    MainViewModel(Repository repository) {
        this.repository = repository;
    }

    public void getUsers() {
        disposables.add(repository.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> responseLiveData.setValue(ResponseGetUser.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ResponseGetUser.success(result)),
                        throwable -> responseLiveData.setValue(ResponseGetUser.responseError(throwable))
                ));
    }

    public MutableLiveData<ResponseGetUser> getResponseLiveData() {
        return responseLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}