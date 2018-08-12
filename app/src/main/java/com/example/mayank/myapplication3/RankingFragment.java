package com.example.mayank.myapplication3;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class RankingFragment extends Fragment {
    View myfragment1;
    public static RankingFragment newInstance(String param1, String param2) {
        RankingFragment rankingfragment = new RankingFragment();

        return rankingfragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myfragment1=inflater.inflate(R.layout.fragment_ranking,container,false);

        return myfragment1;

    }
}
