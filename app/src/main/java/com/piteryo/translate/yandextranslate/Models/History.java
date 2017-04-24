package com.piteryo.translate.yandextranslate.Models;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by piter on 22.04.2017.
 */

public class History extends RealmObject {

    @Required
    private String sourceString;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    private boolean isFavorite;

    public String getDictionary() {
        return dictionary;
    }

    public void setDictionary(String dictionary) {
        this.dictionary = dictionary;
    }

    private String dictionary;

    @Required
    private String translationResult;

    public String getTranslationResult() {
        return translationResult;
    }

    public void setTranslationResult(String translationResult) {
        this.translationResult = translationResult;
    }

    @Required
    private String languageSource;

    @Required
    private String targetLanguage;

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public String getLanguageSource() {
        return languageSource;
    }

    public void setLanguageSource(String languageSource) {
        this.languageSource = languageSource;
    }

    public String getSourceString() {
        return sourceString;
    }

    public void setSourceString(String sourceString) {
        this.sourceString = sourceString;
    }
}