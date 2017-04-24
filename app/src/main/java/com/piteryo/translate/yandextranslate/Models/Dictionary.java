package com.piteryo.translate.yandextranslate.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.piteryo.translate.yandextranslate.Models.Definition;

public class Dictionary {

    @SerializedName("def")
    @Expose
    private List<Definition> definitions = null;


    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

}