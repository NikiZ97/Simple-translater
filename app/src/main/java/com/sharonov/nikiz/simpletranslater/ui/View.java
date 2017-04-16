package com.sharonov.nikiz.simpletranslater.ui;


import com.sharonov.nikiz.simpletranslater.model.data.LanguagesList;

public interface View {
    void showLanguages(LanguagesList list);
    void showTranslatedText(String text);
}
