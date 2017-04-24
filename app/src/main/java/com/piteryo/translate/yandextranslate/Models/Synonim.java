
package com.piteryo.translate.yandextranslate.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Synonim {

    @SerializedName("text")
    @Expose
    private String text;


    @SerializedName("gen")
    @Expose
    private String gen;

    public String getGen() {
        if (gen == null) return "";
        return " " + gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
