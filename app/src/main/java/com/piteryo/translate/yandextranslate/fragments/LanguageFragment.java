package com.piteryo.translate.yandextranslate.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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

import com.piteryo.translate.yandextranslate.App;
import com.piteryo.translate.yandextranslate.Interfaces.SetLanguages;
import com.piteryo.translate.yandextranslate.Models.LanguageList;
import com.piteryo.translate.yandextranslate.Presenters.LanguagesListPresenter;
import com.piteryo.translate.yandextranslate.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class LanguageFragment extends BaseFragment implements SetLanguages {

    // TODO: Customize parameter argument names
    private static final String ARG_LANGUAGE = "mLanguage";
    // TODO: Customize parameters
    private int mLanguage = 1;
    private RecyclerView mRecyclerView;
    LanguagesListPresenter mLanguagesListPresenter;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LanguageFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static LanguageFragment newInstance(int language) {
        LanguageFragment fragment = new LanguageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LANGUAGE, language);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLanguage = getArguments().getInt(ARG_LANGUAGE);
        }
    }

       @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_top, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language_list, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_history);
        if (mLanguage == 0)
            toolbar.setTitle(R.string.source_language);
        else
            toolbar.setTitle(R.string.target_language);
        toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayout);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), linearLayout.getOrientation()));
        mLanguagesListPresenter = new LanguagesListPresenter(this);
        mLanguagesListPresenter.getLanguages();


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setAdapter(LanguageList list) {
        MylanguageRecyclerViewAdapter adapter = new MylanguageRecyclerViewAdapter(list, mListener, mLanguage);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(String item, LanguageList list, int language);
    }
}
