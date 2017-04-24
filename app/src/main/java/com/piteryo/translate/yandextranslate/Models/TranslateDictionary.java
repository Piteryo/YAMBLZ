
package com.piteryo.translate.yandextranslate.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TranslateDictionary {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("pos")
    @Expose
    private String pos;
    @SerializedName("syn")
    @Expose
    private List<Synonim> synonim = null;
    @SerializedName("mean")
    @Expose
    private List<Mean> mean = null;
    @SerializedName("ex")
    @Expose
    private List<Example> example = null;

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

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public List<Synonim> getSynonim() {
        return synonim;
    }

    public void setSynonim(List<Synonim> synonim) {
        this.synonim = synonim;
    }

    public List<Mean> getMean() {
        return mean;
    }

    public void setMean(List<Mean> mean) {
        this.mean = mean;
    }

    public List<Example> getExample() {
        return example;
    }

    public void setExample(List<Example> example) {
        this.example = example;
    }

}
