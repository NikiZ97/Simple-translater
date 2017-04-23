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

    /**
     * This method gets language list, using rx
     */
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

    /**
     * This method gets translate, using rx
     * @param text - original text received from EditText
     * @param languageTo - language to which the text will be translated
     */
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

    /**
     * This method triggered when star image was pressed so
     * the method finds the last element in realm and sets it starred
     */
    @Override
    public void onStarred() {
        realm.beginTransaction();
        realm.where(HistoryElement.class).findAll().last().setStarred(true);
        realm.commitTransaction();
    }

    /**
     * This method closes realm and unsubscribes subscription
     */
    @Override
    public void onStop() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        realm.close();
    }

    /**
     * This method prints an error message to the log
     * @param t - Throwable object
     */
    private void processError(Throwable t) {
        Log.e("TAG", t.getLocalizedMessage(), t);
    }

    /**
     * This method adds text and translated text to realm
     * @param text - original text
     * @param translatedText - translated text
     */
    private void addToHistory(String text, String translatedText) {
        realm.beginTransaction();
        HistoryElement element = realm.createObject(HistoryElement.class);
        element.setOriginalText(text);
        element.setTranslatedText(translatedText);
        realm.commitTransaction();
    }
}
