package com.sharonov.nikiz.simpletranslater.model.data;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslatedText {

    @SerializedName("text")
    private List<String> text;

    @SerializedName("lang")
    private String lang;

    public List<String> getText() {
        return text;
    }

    public String getLang() {
        return lang;
    }

    @Override
    public String toString() {
        return "TranslatedText{" +
                "text=" + text.get(0) +
                ", lang='" + lang + '\'' +
                '}';
    }
}
