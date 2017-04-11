package com.sharonov.nikiz.simpletranslater.ui;


import com.sharonov.nikiz.simpletranslater.model.data.LanguagesList;
import com.sharonov.nikiz.simpletranslater.model.data.TranslatedText;

public interface View {
    void showLanguages(LanguagesList list);
    void showTranslatedText(TranslatedText text);
}
