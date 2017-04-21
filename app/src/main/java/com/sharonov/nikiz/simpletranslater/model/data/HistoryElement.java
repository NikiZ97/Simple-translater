package com.sharonov.nikiz.simpletranslater.model.data;


import io.realm.RealmObject;

public class HistoryElement extends RealmObject {
    private String originalText;
    private String translatedText;

    public HistoryElement() {}

    public HistoryElement(String originalText, String translatedText) {
        this.originalText = originalText;
        this.translatedText = translatedText;
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }
}
