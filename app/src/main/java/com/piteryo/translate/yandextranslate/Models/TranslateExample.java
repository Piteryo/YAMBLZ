
package com.piteryo.translate.yandextranslate.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TranslateExample {

    @SerializedName("text")
    @Expose
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
