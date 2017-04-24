package com.piteryo.translate.yandextranslate.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by piter on 21.04.2017.
 */

public class LanguageList {
    @SerializedName("dirs")
    @Expose
    private List<String> directions;
    @SerializedName("langs")
    @Expose
    private Map<String, String> languages;

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    public Map<String, String> getLanguages() {
        return languages;
    }

    public void setLanguages(Map<String, String> languages) {
        this.languages = languages;
    }


}
