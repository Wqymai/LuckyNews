package com.android.luckynews.news.presenter;

/**
 * Created by wuqiyan on 17/1/19.
 */
public interface INewsPresenter {
    void loadNews(int type,int page,boolean isRefresh);
}
