
package com.piteryo.translate.yandextranslate.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("tr")
    @Expose
    private List<TranslateExample> translate = null;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<TranslateExample> getTranslate() {
        return translate;
    }

    public void setTranslate(List<TranslateExample> translate) {
        this.translate = translate;
    }

}
