package com.piteryo.translate.yandextranslate.Presenters;

import com.piteryo.translate.yandextranslate.App;
import com.piteryo.translate.yandextranslate.Interfaces.SetLanguages;
import com.piteryo.translate.yandextranslate.Models.LanguageList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by piter on 22.04.2017.
 */

public class LanguagesListPresenter {

    private LanguageList mLanguageList;
    private LanguageList mResponse;
    private SetLanguages mSetLanguages;


    public LanguagesListPresenter(SetLanguages setLanguages) {
        mSetLanguages = setLanguages;
    }
    public LanguagesListPresenter(){}

    public void getLanguages() {
        App
                .getTranslateApi()
                .getLanguages("en")
                .enqueue(new Callback<LanguageList>() {

                    @Override
                    public void onResponse(Call<LanguageList> call, Response<LanguageList> response) {
                        App.ShortFullNameMap = response.body().getLanguages();
                        if (mSetLanguages != null)
                            mSetLanguages.setAdapter(response.body());
                    }

                    @Override
                    public void onFailure(Call<LanguageList> call, Throwable t) {
                        try {
                            throw new InterruptedException("Error!");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
