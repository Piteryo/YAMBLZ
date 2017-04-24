package com.piteryo.translate.yandextranslate.Interfaces;

/**
 * Created by piter on 20.04.2017.
 */

public interface TranslateView {
    void setText(String text);
    void setLanguagesViews();
    void setDictionaryView(String html);
    void writeToDb();

}
