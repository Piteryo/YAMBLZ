package com.piteryo.translate.yandextranslate.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piteryo.translate.yandextranslate.App;
import com.piteryo.translate.yandextranslate.Interfaces.SetLanguages;
import com.piteryo.translate.yandextranslate.Models.LanguageList;
import com.piteryo.translate.yandextranslate.R;
import com.piteryo.translate.yandextranslate.fragments.LanguageFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MylanguageRecyclerViewAdapter extends RecyclerView.Adapter<MylanguageRecyclerViewAdapter.ViewHolder>{

    private final LanguageList mValues;
    private final int mLanguage;
    private final OnListFragmentInteractionListener mListener;

    public MylanguageRecyclerViewAdapter(LanguageList items, OnListFragmentInteractionListener listener, int mLanguage) {
        this.mLanguage = mLanguage;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_language, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        List<String> list = new ArrayList<String>(mValues.getLanguages().values());
        Collections.sort(list, new Comparator<String>()
        {
            @Override
            public int compare(String text1, String text2)
            {
                return text1.compareToIgnoreCase(text2);
            }
        });
        holder.mItem = list.get(position);
        holder.mContentView.setText(list.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem, mValues, mLanguage);
                }
            }
        });
    }

    /*@Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setSourceString(mValues.get(position).id);
        holder.mContentView.setSourceString(mValues.get(position).content);


        });
    }*/

    @Override
    public int getItemCount() {
        return mValues.getLanguages().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public String mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
        }

    }
}
