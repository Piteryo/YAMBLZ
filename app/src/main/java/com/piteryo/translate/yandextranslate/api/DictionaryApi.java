package com.piteryo.translate.yandextranslate.api;

import com.piteryo.translate.yandextranslate.Models.Dictionary;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by piter on 24.04.2017.
 */

public interface DictionaryApi {
    @GET("api/v1/dicservice.json/lookup?SHORT_POS=0x0002")
    Call<Dictionary> getDictionaryArticle(@Query("lang") String lang, @Query("text") String text);
}
