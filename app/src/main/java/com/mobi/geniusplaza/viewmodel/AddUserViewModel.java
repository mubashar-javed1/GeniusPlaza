package com.mobi.geniusplaza.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.mobi.geniusplaza.data.User;
import com.mobi.geniusplaza.networkcall.ResponseCreateUser;
import com.mobi.geniusplaza.repository.Repository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AddUserViewModel extends ViewModel {
    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ResponseCreateUser> responseLiveData = new MutableLiveData<>();

    AddUserViewModel(Repository repository) {
        this.repository = repository;
    }

    public void createUser(User user) {
        disposables.add(repository.createUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> responseLiveData.setValue(ResponseCreateUser.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ResponseCreateUser.success(result)),
                        throwable -> responseLiveData.setValue(ResponseCreateUser.responseError(throwable))
                ));
    }

    public MutableLiveData<ResponseCreateUser> getResponseLiveData() {
        return responseLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}