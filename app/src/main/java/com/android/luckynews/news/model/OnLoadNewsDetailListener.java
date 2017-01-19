package com.android.luckynews.news.model;

import com.android.luckynews.bean.NewsDetailBean;

/**
 * Created by wuqiyan on 17/1/19.
 */
public interface OnLoadNewsDetailListener {
    void onSuccess(NewsDetailBean newsDetailBean);

    void onFailure(String msg, Exception e);
}
