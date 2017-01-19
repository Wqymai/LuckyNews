package com.android.luckynews.news.view;

import com.android.luckynews.bean.NewsBean;

import java.util.List;

/**
 * Created by wuqiyan on 17/1/19.
 */
public interface NewsView {
    void showProgress();
    void addNews(List<NewsBean> newsList);
    void hideProgress();
    void showLoadFailMsg();

}
