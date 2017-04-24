package com.piteryo.translate.yandextranslate.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.piteryo.translate.yandextranslate.Models.History;
import com.piteryo.translate.yandextranslate.R;

import java.util.List;

import io.realm.Realm;

/**
 * Created by piter on 22.04.2017.
 */

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {

    private final List<History> mValues;
    private View mView;
    private Realm mRealm;
    private final HistoryFragment.OnHistoryFragmentInteractionListener mListener;

    public HistoryRecyclerViewAdapter(List<History> mValues, HistoryFragment.OnHistoryFragmentInteractionListener mListener, Realm realm) {
        this.mValues = mValues;
        this.mListener = mListener;
        mRealm = realm;
    }

    @Override
    public HistoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_history_item, parent, false);
        return new HistoryRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HistoryRecyclerViewAdapter.ViewHolder holder, int position) {


        holder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                History updatingItem = mRealm.where(History.class)
                        .equalTo("sourceString", holder.mItem.getSourceString())
                        .equalTo("languageSource", holder.mItem.getLanguageSource())
                        .equalTo("targetLanguage", holder.mItem.getTargetLanguage())
                        .findFirst();
                mRealm.beginTransaction();
                updatingItem.setFavorite(!updatingItem.isFavorite());
                mRealm.commitTransaction();
                if (updatingItem.isFavorite())
                {
                    holder.mImageButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
                else
                {
                    holder.mImageButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);

                }
            }
        });
        holder.mItem = mValues.get(position);
        if (holder.mItem.isFavorite())
            holder.mImageButton.setImageResource(R.drawable.ic_favorite_black_24dp);
        else
            holder.mImageButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        holder.mResultView.setText(mValues.get(position).getTranslationResult());
        holder.mSourceView.setText(mValues.get(position).getSourceString());
        holder.mLangs.setText(mValues.get(position).getLanguageSource() + "->" + mValues.get(position).getTargetLanguage());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onHistoryFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView mResultView;
        public TextView mSourceView;
        public TextView mLangs;
        public ImageButton mImageButton;
        public History mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mSourceView = (TextView) itemView.findViewById(R.id.content_history_source);
            mResultView = (TextView) itemView.findViewById(R.id.content_history_result);
            mLangs = (TextView) itemView.findViewById(R.id.content_history_langs);
            mImageButton = (ImageButton) itemView.findViewById(R.id.favoritesImage);
        }
    }
}
