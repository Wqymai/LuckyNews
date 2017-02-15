package com.android.luckynews.images.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.android.luckynews.bean.ImageBean;
import com.android.luckynews.images.ImageAdapter;
import com.android.luckynews.images.presenter.ImagePresenter;
import com.android.luckynews.images.presenter.ImagePresenterImpl;
import com.android.luckynews.images.view.ImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wuqiyan on 17/2/7.
 */

public class ImageFragment extends Fragment implements ImageView,SwipeRefreshLayout.OnRefreshListener {


    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ImageAdapter mAdapter;
    private List<ImageBean> mData;
    private ImagePresenter mImagePresenter;
    private int i=0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImagePresenter=new ImagePresenterImpl(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_image,null);
        mSwipeRefreshWidget= (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.primary,
                R.color.primary_dark, R.color.primary_light,
                R.color.accent);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter=new ImageAdapter(getActivity().getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        onRefresh();
        return view;
    }

    private  RecyclerView.OnScrollListener mOnScrollListener=new RecyclerView.OnScrollListener() {
        private int lastVisibleItem;
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount() ) {
                //加载更多
                Snackbar.make(getActivity().findViewById(R.id.drawer_layout), getString(R.string.image_hit), Snackbar.LENGTH_SHORT).show();
//                ImageBean imageBean=new ImageBean();
//                imageBean.setTitle("这是测试！！！"+(i++));
//                imageBean.setThumburl("http://down.laifudao.com/images/tupian/2015226144710.jpg");
//                mData.add(imageBean);
//                mAdapter.notifyDataSetChanged();

//                mImagePresenter.loadImageList();

            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
        }
    };

    @Override
    public void onRefresh() {
        mImagePresenter.loadImageList();

        Log.i("TAG","下拉刷新了。。。");

    }

    @Override
    public void addImages(List<ImageBean> list) {
        if(mData == null) {
            mData = new ArrayList<>();
        }
//        mData.clear();
//        ImageBean imageBean=new ImageBean();
//        imageBean.setTitle("这是测试！！！"+(i++));
//        imageBean.setThumburl("http://ww1.sinaimg.cn/large/bd759d6djw1epnsrry0f0j20c80g5mxz.jpg");
//        imageBean.setSourceurl("http://down.laifudao.com/images/tupian/2015226144710.jpg");
//        imageBean.setHeight(581);
//        imageBean.setWidth(440);
//        list.add(imageBean);
        mData.addAll(list);
        mAdapter.setmDate(mData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        mSwipeRefreshWidget.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshWidget.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {
        if (isAdded()) {
            View view = getActivity() == null ? mRecyclerView.getRootView() : getActivity().findViewById(R.id.drawer_layout);
            Snackbar.make(view, getString(R.string.load_fail), Snackbar.LENGTH_SHORT).show();
        }
    }
}
