package com.sharonov.nikiz.simpletranslater.presenter;


import com.sharonov.nikiz.simpletranslater.model.data.HistoryElement;

import java.util.List;

public interface Presenter {
    void getLanguages();
    void getTranslate(String text, String languageTo);
    void onAddedToHistory(List<HistoryElement> historyElements);
    void onStarred();
    void onStop();
}
