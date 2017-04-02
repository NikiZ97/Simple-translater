package com.sharonov.nikiz.simpletranslater.presenter;


public interface Presenter {
    void getLanguages();
    void onEditTextChanged();
    void onAddedToHistory();
    void onStarred();
    void onStop();
}
