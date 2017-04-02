package com.sharonov.nikiz.simpletranslater.presenter;


import android.util.Log;

import com.sharonov.nikiz.simpletranslater.model.api.ApiInterface;
import com.sharonov.nikiz.simpletranslater.model.api.ApiModule;
import com.sharonov.nikiz.simpletranslater.model.data.LanguagesList;
import com.sharonov.nikiz.simpletranslater.ui.View;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PresenterImpl implements Presenter {

    private View view;
    private ApiInterface apiInterface = ApiModule.providesApiInterface();

    public PresenterImpl(View view) {
        this.view = view;
    }

    @Override
    public void getLanguages() {
        Observable<LanguagesList> observable =
                apiInterface.getLanguageList(ApiInterface.API_KEY, "ru")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscription subscription = observable.subscribe(view::showLanguages, this::processError);
    }

    @Override
    public void onEditTextChanged() {

    }

    @Override
    public void onAddedToHistory() {

    }

    @Override
    public void onStarred() {

    }

    @Override
    public void onStop() {

    }

    private void processError(Throwable t) {
        Log.e("TAG", t.getLocalizedMessage(), t);
    }
}
