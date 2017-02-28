package com.android.luckynews.news.model;

/**
 * Created by wuqiyan on 17/1/19.
 */
public interface INewsModel {
    void loadNews(String url, int type, OnLoadNewsListListener listener,boolean isRefresh,boolean isSaveCache);

    void loadNewsDetail(String docid, OnLoadNewsDetailListener listener);

}
