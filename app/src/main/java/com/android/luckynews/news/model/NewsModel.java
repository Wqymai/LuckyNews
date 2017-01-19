package com.android.luckynews.news.model;

/**
 * Created by wuqiyan on 17/1/19.
 */
public interface NewsModel {
    void loadNews(String url, int type, OnLoadNewsListListener listener);

    void loadNewsDetail(String docid, OnLoadNewsDetailListener listener);

}
