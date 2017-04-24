
package com.piteryo.translate.yandextranslate.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Definition {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("pos")
    @Expose
    private String PartOfSpeach;
    @SerializedName("tr")
    @Expose
    private List<TranslateDictionary> translateDictionary = null;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPartOfSpeach() {
        return PartOfSpeach;
    }

    public void setPartOfSpeach(String partOfSpeach) {
        this.PartOfSpeach = partOfSpeach;
    }

    public List<TranslateDictionary> getTranslateDictionary() {
        return translateDictionary;
    }

    public void setTranslateDictionary(List<TranslateDictionary> translateDictionary) {
        this.translateDictionary = translateDictionary;
    }

}
