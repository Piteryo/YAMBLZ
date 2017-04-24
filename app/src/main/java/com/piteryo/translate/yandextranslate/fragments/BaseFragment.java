package com.piteryo.translate.yandextranslate.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.piteryo.translate.yandextranslate.R;

/**
 * Created by piter on 18.04.2017.
 */

public class BaseFragment extends Fragment {
    public static final String ARGS_INSTANCE = "com.piteryo.translate.yandextranslate";

    FragmentNavigation mFragmentNavigation;

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigation) {
            mFragmentNavigation = (FragmentNavigation) context;
        }
    }

    public interface FragmentNavigation {
        public void pushFragment(android.support.v4.app.Fragment fragment);
    }

}
