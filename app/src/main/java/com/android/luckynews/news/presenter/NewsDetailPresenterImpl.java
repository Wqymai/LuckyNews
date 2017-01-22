package com.android.luckynews.news.presenter;

import android.content.Context;

import com.android.luckynews.bean.NewsDetailBean;
import com.android.luckynews.news.model.INewsModel;
import com.android.luckynews.news.model.NewsModelImpl;
import com.android.luckynews.news.model.OnLoadNewsDetailListener;
import com.android.luckynews.news.view.INewsDetailView;

/**
 * Created by wuqiyan on 17/1/22.
 */
public class NewsDetailPresenterImpl implements INewsDetailPresenter,OnLoadNewsDetailListener {
    private Context mContent;
    private INewsDetailView mNewsDetailView;
    private INewsModel mNewsModel;


    public NewsDetailPresenterImpl(Context pContent,INewsDetailView pNewsDetailView){
        this.mContent=pContent;
        this.mNewsDetailView=pNewsDetailView;
        mNewsModel=new NewsModelImpl();
    }
    @Override
    public void loadNewsDetail(String docId) {
        mNewsDetailView.showProgress();
        mNewsModel.loadNewsDetail(docId,this);

    }

    @Override
    public void onSuccess(NewsDetailBean newsDetailBean) {
       if (newsDetailBean!=null){
           mNewsDetailView.showNewsDetailContent(newsDetailBean.getBody());
       }
        mNewsDetailView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mNewsDetailView.hideProgress();

    }
}
