package com.piteryo.translate.yandextranslate.Presenters;

import android.content.Context;
import android.support.v7.preference.PreferenceManager;
import android.text.TextUtils;

import com.piteryo.translate.yandextranslate.App;
import com.piteryo.translate.yandextranslate.Interfaces.TranslateView;
import com.piteryo.translate.yandextranslate.Models.History;
import com.piteryo.translate.yandextranslate.Models.LanguageList;
import com.piteryo.translate.yandextranslate.Models.ResultTranslate;

import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by piter on 20.04.2017.
 */

public class TranslatePresenter {
    private int REC_ITER = 0;
    private TranslateView mTranslateView;
    private LanguageList mLanguageList;
    private Context mContext;
    public TranslatePresenter(TranslateView translateView, Context context) {
        mTranslateView = translateView;
        mContext = context;
    }

    public void writeToDb(Realm realm, String sourceText, String destText, String sourceLang, String destLang, Boolean isFavorite)
    {
        realm.beginTransaction();
        History history = realm.createObject(History.class);
        history.setFavorite(isFavorite);
        history.setSourceString(sourceText);
        history.setTranslationResult(destText);
        history.setLanguageSource(sourceLang);
        history.setTargetLanguage(destLang);
        realm.commitTransaction();
    }


    public void getTranslate(final String inputText, final String languageFrom, final String languageTo) {
        String hyphen = languageFrom.equals("") ? "" : "-";
        App
                .getTranslateApi()
                .getData(languageFrom + hyphen + languageTo, inputText)
                .enqueue(new Callback<ResultTranslate>() {
                    @Override
                    public void onResponse(Call<ResultTranslate> call, Response<ResultTranslate> response) {
                        if (response.body() != null) {
                            List<String> responseResult = response.body().getText();
                            String resultString = TextUtils.join(" ", responseResult);
                            if (resultString.equals(inputText) && REC_ITER == 0)
                            {
                                ++REC_ITER;
                                getTranslate(inputText, "", languageTo);
                                return;
                            }
                            String detectedLanguage = response.body().getDetected().getLang();
                            if (!Objects.equals(detectedLanguage, languageFrom) && REC_ITER > 0) {
                                PreferenceManager.getDefaultSharedPreferences(mContext).edit().putString("langFrom", detectedLanguage).apply();
                                PreferenceManager.getDefaultSharedPreferences(mContext).edit().putString("fullNameLangFrom", App.ShortFullNameMap.get(detectedLanguage)).apply();
                                mTranslateView.setLanguagesViews();
                            }
                            REC_ITER = 0;
                            mTranslateView.setText(resultString);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultTranslate> call, Throwable t) {
                        try {
                            throw new InterruptedException("Error!");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
