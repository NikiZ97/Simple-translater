package com.sharonov.nikiz.simpletranslater.model.api;


import com.sharonov.nikiz.simpletranslater.model.data.LanguagesList;
import com.sharonov.nikiz.simpletranslater.model.data.TranslatedText;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

public interface ApiInterface {

    String API_KEY = "trnsl.1.1.20170315T174318Z.1b6c479f76583a42.19dbccee5df6a83518dd65ff33436533d4bcd55e";

    @GET("api/v1.5/tr.json/getLangs?")
    Observable<LanguagesList> getLanguageList(@Query("key") String apiKey, @Query("ui") String ui);

    @POST("api/v1.5/tr.json/translate?")
    Observable<TranslatedText> getTranslate(@Query("key") String apiKey, @Query("text") String text,
                                            @Query("lang") String language);

}
