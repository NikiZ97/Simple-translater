package com.sharonov.nikiz.simpletranslater.presenter;


public interface Presenter {
    void getLanguages();
    void getTranslate(String text, String languageTo);
    void onStarred();
    void onStop();
}
