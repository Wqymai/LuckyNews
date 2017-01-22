package com.android.luckynews.news.model;

import com.android.luckynews.bean.NewsBean;

import java.util.List;

/**
 * Created by wuqiyan on 17/1/19.
 */
public interface OnLoadNewsListListener {
    void onSuccess(List<NewsBean> list);
    void onFailure(String msg, Exception e);
}
