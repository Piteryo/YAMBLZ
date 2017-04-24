package com.piteryo.translate.yandextranslate.Models;

import android.util.Pair;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultTranslate {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("text")
    @Expose
    private List<String> text = null;



    public class Detected {

        @SerializedName("lang")
        @Expose
        private String lang;

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

    }

    public Detected getDetected() {
        return detected;
    }

    public void setDetected(Detected detected) {
        this.detected = detected;
    }

    @SerializedName("detected")
    @Expose
    private Detected detected;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

}