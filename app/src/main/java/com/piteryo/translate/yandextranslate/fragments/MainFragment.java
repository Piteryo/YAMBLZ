package com.piteryo.translate.yandextranslate.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.piteryo.translate.yandextranslate.Interfaces.TranslateView;
import com.piteryo.translate.yandextranslate.Presenters.DictionaryPresenter;
import com.piteryo.translate.yandextranslate.Presenters.LanguagesListPresenter;
import com.piteryo.translate.yandextranslate.Presenters.TranslatePresenter;
import com.piteryo.translate.yandextranslate.R;

import io.realm.Realm;

/**
 * Created by piter on 18.04.2017.
 */

public class MainFragment extends BaseFragment implements TranslateView {
    private TranslatePresenter mTranslatePresenter;
    private TextView mTextView;
    private TextView mLangFrom;
    private TextView mLangTo;
    private String mLangFromString;
    private String mFullLangFromString;
    private String mLangToString;
    private String mFullLangToString;
    private Realm mRealm;
    private ImageButton mImageButton;
    private TextView mTranslateDictionary;
    DictionaryPresenter mDictPresenter;


    EditText mEditText;
    int mInt = 0;

    public static MainFragment newInstance(int instance) {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mTranslatePresenter = new TranslatePresenter(this, getContext());
        mRealm = Realm.getDefaultInstance();
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_main, container, false);
        ImageButton imageButton = (ImageButton) mView.findViewById(R.id.change_lang);
        imageButton.setOnClickListener(new SwapButtonListener());
        Toolbar toolbar = (Toolbar) mView.findViewById(R.id.toolbar_main_fragment);
        mTranslateDictionary = (TextView)mView.findViewById(R.id.translateViewDictionary);
        mLangFrom = (TextView)mView.findViewById(R.id.lang_from);
        mLangTo = (TextView) mView.findViewById(R.id.lang_to);
        setLanguagesViews();
        mTextView = (TextView) mView.findViewById(R.id.translateView);
        mTextView.setMovementMethod(new ScrollingMovementMethod());
        mEditText = (EditText) mView.findViewById(R.id.editText);
        mEditText.setFocusableInTouchMode(true);

        mEditText.requestFocus();
        mEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        mDictPresenter = new DictionaryPresenter(this);
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mTranslatePresenter.getTranslate(mEditText.getText().toString(), mLangFromString, mLangToString);
                    mDictPresenter.getDictionaryArticle(mEditText.getText().toString(), mLangFromString + "-" + mLangToString);
                }
                return false;
            }
        });

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(null);
        return mView;
        }

    @Override
    public void setText(String text)
    {
        mTextView.setText(text);
        writeToDb();
    }


    @Override
    public void setLanguagesViews() {
        mLangFromString = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("langFrom", "en");
        mLangToString = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("langTo", "ru");
        mFullLangToString = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("fullNameLangTo", "Russian");
        mFullLangFromString = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("fullNameLangFrom", "English");

        mLangFrom.setText(mFullLangFromString);
        mLangFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentNavigation != null)
                {
                    mFragmentNavigation.pushFragment(LanguageFragment.newInstance(0));
                }
            }
        });
        mLangTo.setText(mFullLangToString);
        mLangTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentNavigation != null)
                {
                    mFragmentNavigation.pushFragment(LanguageFragment.newInstance(1));
                }
            }
        });
    }

    @Override
    public void setDictionaryView(String html) {
        if (html != "" && html != null) {
            mTranslateDictionary.setMovementMethod(new ScrollingMovementMethod());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTranslateDictionary.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT));
            } else {
                mTranslateDictionary.setText(Html.fromHtml(html));
            }
        }
    }

    @Override
    public void writeToDb() {
        mTranslatePresenter.writeToDb(mRealm, mEditText.getText().toString(), mTextView.getText().toString(), mLangFromString, mLangToString, false);
    }

    public void fillViewsWithHistory(String textViewContent, String editViewContent, String translateDictContent)
    {
        mTextView.setText(textViewContent);
        mEditText.setText(editViewContent);
        setDictionaryView(translateDictContent);
        setLanguagesViews();
    }


    private class SwapButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("langFrom", mLangToString).apply();
            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("fullNameLangFrom", mFullLangToString).apply();
            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("langTo", mLangFromString).apply();
            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("fullNameLangTo", mFullLangFromString).apply();
            String buff;
            buff = mLangFromString;
            mLangFromString = mLangToString;
            mLangToString = buff;
            buff = mFullLangFromString;
            mFullLangFromString = mFullLangToString;
            mFullLangToString = buff;
            mLangFrom.setText(mFullLangFromString);
            mLangTo.setText(mFullLangToString);
        }
    }
}
