package com.android.luckynews.news.view;

/**
 * Created by wuqiyan on 17/1/19.
 */
public interface NewsDetailView {
    void showNewsDetailContent(String newsDetailContent);
    void showProgress();
    void hideProgress();
}
