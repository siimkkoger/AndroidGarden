package com.siimk.garden;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by siimk on 6/11/2017.
 */

public class FragmentGardenMap extends Fragment {
    private static final String TAG = "Tab1Fragment";

    private Garden garden;
    private Bed bed;

    public FragmentGardenMap(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        garden = getArguments().getParcelable("send_garden");
        bed = getArguments().getParcelable("send_bed");
        Log.d(TAG, "onTouch3: " + garden.getViewWidth() + " " + garden.getViewHeight());
        View view = new BedView(getContext(), garden, false, bed);
        return view;
    }
}
