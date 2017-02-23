package com.android.luckynews.news.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.luckynews.R;
import com.android.luckynews.bean.NewsBean;
import com.android.luckynews.commons.Urls;
import com.android.luckynews.news.NewsAdapter;
import com.android.luckynews.news.presenter.INewsPresenter;
import com.android.luckynews.news.presenter.NewsPresenterImpl;
import com.android.luckynews.news.view.INewsView;
import com.android.luckynews.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import static android.R.transition.move;

/**
 * Created by wuqiyan on 17/1/19.
 */
public class NewsListFragment extends Fragment implements INewsView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "NewsListFragment";

    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private NewsAdapter mAdapter;
    private List<NewsBean> mData;
    private INewsPresenter mNewsPresenter;

    private int mType = NewsFragment.NEWS_TYPE_TOP;
    private int pageIndex = 0;

    public static NewsListFragment newInstance(int type) {
        Bundle args = new Bundle();
        NewsListFragment fragment = new NewsListFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsPresenter = new NewsPresenterImpl(this);
        mType = getArguments().getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newslist, null);

        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.primary,
                R.color.primary_dark, R.color.primary_light,
                R.color.accent);
        mSwipeRefreshWidget.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new NewsAdapter(getActivity().getApplicationContext());
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        firstLoad();
        return view;
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()
                    && mAdapter.isShowFooter()) {
                //加载更多
                LogUtils.d(TAG, "loading more data");
                mNewsPresenter.loadNews(mType, pageIndex + Urls.PAZE_SIZE,true);
            }
        }
    };

    private NewsAdapter.OnItemClickListener mOnItemClickListener = new NewsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (mData.size() <= 0) {
                return;
            }
            NewsBean news = mAdapter.getItem(position);
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra("news", news);

            View transitionView = view.findViewById(R.id.ivNews);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            transitionView, getString(R.string.transition_news_img));

            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    };

    @Override
    public void showProgress() {
        mSwipeRefreshWidget.setRefreshing(true);
    }

    @Override
    public void addNews(List<NewsBean> newsList) {
        if(mData == null) {
            mData = new ArrayList<>();
        }
        if (newsList!=null&&newsList.size()>0){
            mAdapter.isShowFooter(true);
            mData.addAll(newsList);
        }
        if(pageIndex == 0) {
            mAdapter.setmData(mData);
        } else {
            if(newsList == null || newsList.size() == 0) {
                View view = getActivity() == null ? mRecyclerView.getRootView() : getActivity().findViewById(R.id.drawer_layout);
                Snackbar.make(view, getString(R.string.image_hit), Snackbar.LENGTH_SHORT).show();
                smoothMoveToPosition();
            }
            mAdapter.notifyDataSetChanged();
        }
        pageIndex += Urls.PAZE_SIZE;
    }


    @Override
    public void hideProgress() {
        mSwipeRefreshWidget.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {
        if(pageIndex == 0) {
            mAdapter.isShowFooter(false);
            mAdapter.notifyDataSetChanged();
        }
        View view = getActivity() == null ? mRecyclerView.getRootView() : getActivity().findViewById(R.id.drawer_layout);
        Snackbar.make(view, getString(R.string.load_fail), Snackbar.LENGTH_SHORT).show();
        smoothMoveToPosition();
    }

    @Override
    public void onRefresh() {
        pageIndex=0;
        mNewsPresenter.loadNews(mType, pageIndex,true);

    }

    public void firstLoad(){
        pageIndex = 0;
        mNewsPresenter.loadNews(mType, pageIndex,false);
        if (mAdapter.isShowFooter()&& mData==null){
            hideProgress();
        }
    }
    private void smoothMoveToPosition() {
        int firstItem = mLayoutManager.findFirstVisibleItemPosition();
        Log.i("TAG","firstItem："+firstItem);
        int lastItem = mLayoutManager.findLastVisibleItemPosition();
//        Log.i("TAG","lastItem："+lastItem);
//        if (n <= firstItem ){
//            mRecyclerView.smoothScrollToPosition(n);
//        }else if ( n <= lastItem ){
//            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
//            mRecyclerView.smoothScrollBy(0, top);
//        }else{
            mRecyclerView.smoothScrollToPosition(firstItem-1);
//            move = true;
//        }

    }

}
