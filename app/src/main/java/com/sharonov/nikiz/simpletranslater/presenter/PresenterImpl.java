package com.sharonov.nikiz.simpletranslater.presenter;


import android.util.Log;

import com.sharonov.nikiz.simpletranslater.model.api.ApiInterface;
import com.sharonov.nikiz.simpletranslater.model.api.ApiModule;
import com.sharonov.nikiz.simpletranslater.model.data.HistoryElement;
import com.sharonov.nikiz.simpletranslater.ui.View;

import io.realm.Realm;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public class PresenterImpl implements Presenter {

    private View view;
    private ApiInterface apiInterface = ApiModule.providesApiInterface();
    private Subscription subscription = Subscriptions.empty();
    private Realm realm;

    public PresenterImpl(View view) {
        this.view = view;
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void getLanguages() {

        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        apiInterface.getLanguageList(ApiInterface.API_KEY, "ru")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showLanguages, this::processError);
    }

    @Override
    public void getTranslate(String text, String languageTo) {
        apiInterface.getTranslate(ApiInterface.API_KEY, text, languageTo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(translatedText -> {
                    addToHistory(text, translatedText.getText().get(0));
                    view.showTranslatedText(translatedText.getText().get(0));
                }, this::processError);
    }

    @Override
    public void onStarred() {
        realm.beginTransaction();
        realm.where(HistoryElement.class).findAll().last().setStarred(true);
        realm.commitTransaction();
    }

    @Override
    public void onStop() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        realm.close();
    }

    private void processError(Throwable t) {
        Log.e("TAG", t.getLocalizedMessage(), t);
    }

    private String saveToRealm(HistoryElement element) {
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(transaction -> {
            HistoryElement historyElement = findInRealm(transaction, element.getOriginalText(),
                    element.getTranslatedText());
            if (historyElement == null) {
                historyElement = transaction.createObject(HistoryElement.class, element.getOriginalText());
            }
            historyElement.setOriginalText(element.getOriginalText());
            historyElement.setTranslatedText(element.getTranslatedText());
        });
        realm.close();
        return element.getOriginalText();
    }

    private HistoryElement findInRealm(Realm realm, String original, String translated) {
        return realm.where(HistoryElement.class)
                .equalTo("originalText", original)
                .equalTo("translatedText", translated)
                .findFirst();
    }

    private void addToHistory(String text, String translatedText) {
        realm.beginTransaction();
        HistoryElement element = realm.createObject(HistoryElement.class);
        element.setOriginalText(text);
        element.setTranslatedText(translatedText);
        realm.commitTransaction();
    }
}
