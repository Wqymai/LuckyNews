package com.android.luckynews.news.widget;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.luckynews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuqiyan on 17/1/19.
 */
public class NewsFragment extends android.support.v4.app.Fragment {
    public static final int NEWS_TYPE_TOP = 0;
    public static final int NEWS_TYPE_NBA = 1;
    public static final int NEWS_TYPE_CARS = 2;
    public static final int NEWS_TYPE_JOKES = 3;
    private TabLayout mTablayout;
    private ViewPager mViewPager;

    private FloatingActionButton mFab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news,null);
//        mFab= (FloatingActionButton) view.findViewById(R.id.fab);
        mTablayout= (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager= (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(3);
        setupViewPager(mViewPager);
        mTablayout.addTab(mTablayout.newTab().setText(R.string.top));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.nba));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.cars));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.jokes));
        mTablayout.setupWithViewPager(mViewPager);
//        mFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(),"fab",Toast.LENGTH_SHORT).show();
//            }
//        });

        return view;
    }
    private void setupViewPager(ViewPager viewPager){
        MyPagerAdapter adapter=new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_TOP), getString(R.string.top));
        adapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_NBA), getString(R.string.nba));
        adapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_CARS), getString(R.string.cars));
        adapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_JOKES), getString(R.string.jokes));
        mViewPager.setAdapter(adapter);

    }
    public static class MyPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragments=new ArrayList<>();
        private final List<String> mFragmentTitles=new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment,String title){
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
