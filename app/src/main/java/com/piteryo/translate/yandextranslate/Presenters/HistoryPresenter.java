package com.piteryo.translate.yandextranslate.Presenters;

import com.piteryo.translate.yandextranslate.Interfaces.HistoryView;
import com.piteryo.translate.yandextranslate.Models.History;
import com.piteryo.translate.yandextranslate.fragments.HistoryRecyclerViewAdapter;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by piter on 22.04.2017.
 */

public class HistoryPresenter {

private HistoryView mHistoryView;
    private Realm mRealm;

    public HistoryPresenter(HistoryView mHistoryView, Realm realm) {
        this.mHistoryView = mHistoryView;
        mRealm = realm;
    }

    public void getHistory()
    {
        RealmResults<History> results = mRealm.where(History.class).findAll();
        List<History> hist = mRealm.copyFromRealm(results);
        mHistoryView.setAdapter(hist);

    }
}
