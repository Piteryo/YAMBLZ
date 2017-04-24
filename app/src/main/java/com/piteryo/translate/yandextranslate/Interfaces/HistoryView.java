package com.piteryo.translate.yandextranslate.Interfaces;

import com.piteryo.translate.yandextranslate.Models.History;

import java.util.List;

/**
 * Created by piter on 22.04.2017.
 */

public interface HistoryView {
    void setAdapter(List<History> history);
}
