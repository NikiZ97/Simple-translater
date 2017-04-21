package com.sharonov.nikiz.simpletranslater.presenter;


import com.sharonov.nikiz.simpletranslater.model.data.HistoryElement;

public interface Presenter {
    void getLanguages();
    void getTranslate(String text, String languageTo);
    HistoryElement onStarred();
    void onStop();
}
