package com.sharonov.nikiz.simpletranslater.model.data;


import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class LanguagesList {

    @SerializedName("langs")
    private Map<String, String> langs;

    public Map<String, String> getLangs() {
        return langs;
    }

}
