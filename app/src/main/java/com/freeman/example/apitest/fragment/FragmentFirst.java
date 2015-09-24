package com.freeman.example.apitest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freeman.example.apitest.FLog;
import com.freeman.example.apitest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by freeman on 9/19/15.
 */
public class FragmentFirst extends FragmentBase {

    private static final String TAG = FragmentFirst.class.getSimpleName();

    private ViewPager mViewPager;
    private MyFragmentStateAdapter mFragmentStateAdapter;
    private TabLayout mTabLayout;

    @Override
    public void onDestroy() {
        super.onDestroy();
        FLog.d(TAG, "onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FLog.d(TAG, "onDestroyView");
    }

    @Override
    public void onStop() {
        super.onStop();
        FLog.d(TAG, "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        FLog.d(TAG, "onPause");
    }

    @Override
    public void onStart() {
        super.onStart();
        FLog.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        FLog.d(TAG, "onResume");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FLog.d(TAG, "onViewCreated");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FLog.d(TAG, "onViewCreated");
        View view = inflater.inflate(R.layout.fragment_viewpager_recycler_layout, null);

        initViewPager(view);

        return  view;
    }

    private void initViewPager (View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.fragment_view_pager);
        mFragmentStateAdapter = new MyFragmentStateAdapter(getChildFragmentManager());

        mTabLayout = (TabLayout) view.findViewById(R.id.fragment_tab_layout);
        TabLayout.Tab androidTab = mTabLayout.newTab();
        androidTab.setText(TabFragment.Android.getTitle());
        mTabLayout.addTab(androidTab);

        TabLayout.Tab iOSTab = mTabLayout.newTab();
        iOSTab.setText(TabFragment.Android.getTitle());
        mTabLayout.addTab(iOSTab);

        mViewPager.setAdapter(mFragmentStateAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
//        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FLog.d(TAG, "onCreate");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        FLog.d(TAG, "onDetach");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FLog.d(TAG, "onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FLog.d(TAG, "onActivityCreated");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        FLog.d(TAG, "setUserVisibleHint: " + isVisibleToUser);
    }

    @Override
    protected String getTipString() {
        return TAG;
    }

    private static class MyFragmentStateAdapter extends FragmentStatePagerAdapter {

        public MyFragmentStateAdapter (FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TabFragment.values()[position].getFragment();
        }

        @Override
        public int getCount() {
            return TabFragment.values().length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TabFragment.values()[position].getTitle();
//            return "Android";
        }
    }
}
