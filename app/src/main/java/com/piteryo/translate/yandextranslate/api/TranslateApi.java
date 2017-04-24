package com.piteryo.translate.yandextranslate.api;

import com.piteryo.translate.yandextranslate.Models.LanguageList;
import com.piteryo.translate.yandextranslate.Models.ResultTranslate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by piter on 20.04.2017.
 */

public interface TranslateApi {
    @GET("/api/v1.5/tr.json/translate?options=1")
    Call<ResultTranslate> getData(@Query("lang") String lang, @Query("text") String text);

    @GET("/api/v1.5/tr.json/getLangs")
    Call<LanguageList> getLanguages( @Query("ui") String ui);
}


