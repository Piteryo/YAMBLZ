package com.piteryo.translate.yandextranslate.Presenters;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.piteryo.translate.yandextranslate.App;
import com.piteryo.translate.yandextranslate.Interfaces.TranslateView;
import com.piteryo.translate.yandextranslate.Models.Definition;
import com.piteryo.translate.yandextranslate.Models.Dictionary;
import com.piteryo.translate.yandextranslate.Models.Example;
import com.piteryo.translate.yandextranslate.Models.LanguageList;
import com.piteryo.translate.yandextranslate.Models.Mean;
import com.piteryo.translate.yandextranslate.Models.Synonim;
import com.piteryo.translate.yandextranslate.Models.TranslateDictionary;
import com.piteryo.translate.yandextranslate.Models.TranslateExample;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by piter on 24.04.2017.
 */

public class DictionaryPresenter {
    TranslateView mTranslateView;
    public DictionaryPresenter(TranslateView translateView) {
        mTranslateView = translateView;
    }

    public String htmlBuilder(Dictionary dictionary)
    {
        if (dictionary == null)
            return "";
        String htmlString = "";
        if (dictionary.getDefinitions() != null)
        for(Definition def : dictionary.getDefinitions())
        {
            htmlString += "<em>"+def.getPartOfSpeach()+"</em><br/>" +
                    "<ul>";
            if (def.getTranslateDictionary() != null)
            for(TranslateDictionary trDict : def.getTranslateDictionary())
            {
                htmlString += String.format("<li> %s<em><span style=\"color: #999999;\">%s</span></em>, ", trDict.getText(), trDict.getGen());
                if (trDict.getSynonim() != null)
                for (Synonim syn : trDict.getSynonim())
                {
                    htmlString += syn.getText() + " <em><span style=\"color: #999999;\">"   + syn.getGen() + "</span></em>, ";
                }
                htmlString = htmlString.substring(0, htmlString.length()-2);
                htmlString += "<br/>" + "<span style=\"color: #ff0000;\">";
                if (trDict.getMean() != null) {
                    htmlString += "(";
                    for (Mean mean : trDict.getMean()) {
                        htmlString += mean.getText() + ", ";
                    }
                    htmlString = htmlString.substring(0, htmlString.length() - 2);
                    htmlString += ")";
                }
                htmlString += "</span><br/>" +
                        "<span style=\"color: #0000ff;\">";
                if (trDict.getExample() != null)
                for (Example ex : trDict.getExample())
                {
                    htmlString += ex.getText() + " - ";
                    if (ex.getTranslate() != null)
                    for (TranslateExample trEx : ex.getTranslate())
                    {
                        htmlString += trEx.getText() + ", ";
                    }
                    htmlString = htmlString.substring(0, htmlString.length()-2);
                    htmlString += "<br/>";
                }
                htmlString += "</li>";


            }


            htmlString += "</ul>";

        }
        return htmlString;
    }


    public void getDictionaryArticle(String text, String lang)
    {
        App
                .getDictionaryApi()
                .getDictionaryArticle(lang, text)
                .enqueue(new Callback<Dictionary>() {

                    @Override
                    public void onResponse(Call<Dictionary> call, Response<Dictionary> response) {
                        Dictionary dict = response.body();
                        String resultHtml = htmlBuilder(dict);
                        mTranslateView.setDictionaryView(resultHtml);
                    }

                    @Override
                    public void onFailure(Call<Dictionary> call, Throwable t) {
                        try {
                            mTranslateView.writeToDb();
                            throw new InterruptedException("Error!");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
