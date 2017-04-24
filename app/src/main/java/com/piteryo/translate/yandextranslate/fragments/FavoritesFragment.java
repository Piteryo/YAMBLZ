package com.piteryo.translate.yandextranslate.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piteryo.translate.yandextranslate.Interfaces.HistoryView;
import com.piteryo.translate.yandextranslate.Models.History;
import com.piteryo.translate.yandextranslate.Presenters.HistoryPresenter;
import com.piteryo.translate.yandextranslate.R;

import java.util.List;

import io.realm.Realm;

/**
 * Created by piter on 18.04.2017.
 */

public class FavoritesFragment extends BaseFragment implements HistoryView {

    TextView mTextView;
    Toolbar mToolbar;
    Realm mRealm;
    HistoryPresenter mSettingsPresenter;
    private RecyclerView mRecyclerView;
    private HistoryFragment.OnHistoryFragmentInteractionListener mListener;


    BaseFragment.FragmentNavigation mFragmentNavigation;
    int mInt = 0;

    public static FavoritesFragment newInstance(int instance) {
        Bundle args = new Bundle();
        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_top, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = Realm.getDefaultInstance();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_history);
        mToolbar.setTitle("Favorites");
        mToolbar.setTitleTextColor(Color.WHITE);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        setHasOptionsMenu(true);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_history);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), linearLayoutManager.getOrientation()));
        mSettingsPresenter = new HistoryPresenter(this, mRealm);
        mSettingsPresenter.getHistory();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HistoryFragment.OnHistoryFragmentInteractionListener) {
            mListener = (HistoryFragment.OnHistoryFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSettingsFragmentInteractionListener");
        }
    }

    @Override
    public void setAdapter(List<History> Settings) {
        List<History> list = mRealm.where(History.class)
                .equalTo("isFavorite", true)
                .findAll();
        HistoryRecyclerViewAdapter adapter = new HistoryRecyclerViewAdapter(list, mListener, mRealm);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


}