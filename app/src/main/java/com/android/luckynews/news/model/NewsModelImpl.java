package com.android.luckynews.news.model;

import com.android.luckynews.bean.NewsBean;
import com.android.luckynews.commons.Urls;
import com.android.luckynews.news.NewsJsonUtils;
import com.android.luckynews.news.widget.NewsFragment;
import com.android.luckynews.utils.OkHttpUtils;

import java.util.List;

/**
 * Created by wuqiyan on 17/1/19.
 */
public class NewsModelImpl implements NewsModel {
    @Override
    public void loadNews(String url, final int type, final OnLoadNewsListListener listener) {
        OkHttpUtils.ResultCallback<String> loadNewsCallback=new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                List<NewsBean> newsBeanList= NewsJsonUtils.readJsonNewsBeans(response,getID(type));
                listener.onSuccess(newsBeanList);
            }
            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load news list failure",e);
            }
        };
        OkHttpUtils.get(url,loadNewsCallback);

    }

    @Override
    public void loadNewsDetail(String docid, OnLoadNewsDetailListener listener) {

    }

    private String getID(int type) {
        String id;
        switch (type) {
            case NewsFragment.NEWS_TYPE_TOP:
                id = Urls.TOP_ID;
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                id = Urls.NBA_ID;
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                id = Urls.CAR_ID;
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                id = Urls.JOKE_ID;
                break;
            default:
                id = Urls.TOP_ID;
                break;
        }
        return id;
    }
}
