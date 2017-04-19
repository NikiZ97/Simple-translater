package com.sharonov.nikiz.simpletranslater.model.data;


public class HistoryElement {
    private String originalText;
    private String translatedText;

    public HistoryElement() {}

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
