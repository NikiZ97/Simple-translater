package com.sharonov.nikiz.simpletranslater.model.api;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

@Module
public class ApiModule {
    private static final String URL = "https://translate.yandex.net/";

    @Singleton
    @Provides
    public static ApiInterface providesApiInterface() {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiInterface.class);
    }
}
