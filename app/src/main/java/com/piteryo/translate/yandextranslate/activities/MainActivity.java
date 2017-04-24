package com.piteryo.translate.yandextranslate.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.ncapdevi.fragnav.FragNavController;
import com.piteryo.translate.yandextranslate.App;
import com.piteryo.translate.yandextranslate.Models.History;
import com.piteryo.translate.yandextranslate.Models.LanguageList;
import com.piteryo.translate.yandextranslate.Presenters.LanguagesListPresenter;
import com.piteryo.translate.yandextranslate.R;
import com.piteryo.translate.yandextranslate.fragments.BaseFragment;
import com.piteryo.translate.yandextranslate.fragments.HistoryFragment;
import com.piteryo.translate.yandextranslate.fragments.LanguageFragment;
import com.piteryo.translate.yandextranslate.fragments.MainFragment;
import com.piteryo.translate.yandextranslate.fragments.FavoritesFragment;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener, LanguageFragment.OnListFragmentInteractionListener, HistoryFragment.OnHistoryFragmentInteractionListener{


    private final int INDEX_HOME = FragNavController.TAB1;
    private final int INDEX_HISTORY = FragNavController.TAB2;
    private final int INDEX_SETTINGS = FragNavController.TAB3;
    public static String MY_PREF = "MY_PREF";
    private Toolbar mToolbarMain;
    private Toolbar mToolbarFavorites;

    private TextView mTextMessage;
    private BottomNavigationView mBottomNavigationView;
    private FragNavController mNavController;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mNavController.switchTab(INDEX_HOME);
                    return true;
                case R.id.navigation_history:
                    mNavController.switchTab(INDEX_HISTORY);
                    return true;
                case R.id.navigation_settings:
                    mNavController.switchTab(INDEX_SETTINGS);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LanguagesListPresenter languageList = new LanguagesListPresenter();
        languageList.getLanguages();
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomBar);
        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.container)
                .transactionListener(this)
                .rootFragmentListener(this, 3)
                .build();

        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mBottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                mNavController.clearStack();
            }
        });


    }


    @Override
    public void onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNavController != null) {
            mNavController.onSaveInstanceState(outState);
        }
    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.pushFragment(fragment);
        }
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }


    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case INDEX_HOME:
                return MainFragment.newInstance(0);
            case INDEX_HISTORY:
                return HistoryFragment.newInstance(0);
            case INDEX_SETTINGS:
                return FavoritesFragment.newInstance(0);
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onListFragmentInteraction(String item, LanguageList list, int language) {
        String keyLang = null;
        Iterator<Map.Entry<String,String>> iter = list.getLanguages().entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String,String> entry = iter.next();
            if (entry.getValue().equals(item)) {
                keyLang = entry.getKey();
            }
        }

        if (language == 0) {
            PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("langFrom", keyLang).apply();
            PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("fullNameLangFrom", item).apply();
        }
        else {
            PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("langTo", keyLang).apply();
            PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("fullNameLangTo", item).apply();
        }
        this.onBackPressed();
    }

    @Override
    public void onHistoryFragmentInteraction(History item) {
        mNavController.switchTab(INDEX_HOME);
        mBottomNavigationView.getMenu().getItem(INDEX_HOME).setChecked(true);
        List<Fragment> allFragments = getSupportFragmentManager().getFragments();

        for(Fragment fragment : allFragments)
        {
            if (fragment instanceof MainFragment)
            {
                PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("langFrom", item.getLanguageSource()).apply();
                PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("fullNameLangFrom", App.ShortFullNameMap.get(item.getLanguageSource())).apply();
                PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("langTo", item.getTargetLanguage()).apply();
                PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("fullNameLangTo", App.ShortFullNameMap.get(item.getTargetLanguage())).apply();
                ((MainFragment) fragment).fillViewsWithHistory(item.getTranslationResult(), item.getSourceString(), item.getDictionary());
            }
        }
    }
}
