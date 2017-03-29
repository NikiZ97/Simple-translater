package com.sharonov.nikiz.simpletranslater.model.api;


import com.sharonov.nikiz.simpletranslater.model.data.LanguagesList;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface ApiInterface {

    @GET("api/v1.5/tr.json/getLangs?")
    Observable<LanguagesList> getLanguageList(@Query("key") String apiKey, @Query("ui") String ui);
}
