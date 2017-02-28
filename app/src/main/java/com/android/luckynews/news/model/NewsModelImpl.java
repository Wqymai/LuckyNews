package com.android.luckynews.news.model;

import android.util.Log;

import com.android.luckynews.bean.NewsBean;
import com.android.luckynews.bean.NewsDetailBean;
import com.android.luckynews.cache.CacheManager;
import com.android.luckynews.commons.Urls;
import com.android.luckynews.news.NewsJsonUtils;
import com.android.luckynews.news.widget.NewsFragment;
import com.android.luckynews.utils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by wuqiyan on 17/1/19.
 */
public class NewsModelImpl implements INewsModel {
    boolean isLoadCache0=true;
    boolean isLoadCache1=true;
    boolean isLoadCache2=true;
    boolean isLoadCache3=true;

    @Override
    public void loadNews(String url, final int type, final OnLoadNewsListListener listener,final boolean isRefresh,final boolean isSaveCache) {
        OkHttpUtils.ResultCallback<String> loadNewsCallback=new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("<html>")){
                    listener.onFailure("load news list failure",new Exception());
                    return;
                }
                if (isSaveCache){
                    Log.i("TAG","save cache");
                    CacheManager.getInstance().putFileCache(getKeyName(type),response,0);
                }
                List<NewsBean> newsBeanList= NewsJsonUtils.readJsonNewsBeans(response,getID(type));
                listener.onSuccess(newsBeanList);
            }
            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load news list failure",e);
            }
        };
        String data=CacheManager.getInstance().getFileCache(getKeyName(type));

        if (data!=null&&!isRefresh){
            List<NewsBean> newsBeanList= NewsJsonUtils.readJsonNewsBeans(data,getID(type));
            if (newsBeanList.size()>0){
                Log.i("TAG","Load cache...");
                listener.onSuccess(newsBeanList);
            }
            else{
                Log.i("TAG","http request ...");
                OkHttpUtils.get(url,loadNewsCallback);
            }
        }
        else {
            Log.i("TAG","http request ...");
            OkHttpUtils.get(url,loadNewsCallback);
        }
    }

    public boolean isLoadCache(int type){
        boolean isLoad = true;
        switch (type){
            case 0:
                isLoad= isLoadCache0;
                isLoadCache0=false;
                break;
            case 1:
                isLoad= isLoadCache1;
                isLoadCache1=false;
                break;
            case 2:
                isLoad=  isLoadCache2;
                isLoadCache2=false;
                break;
            case 3:
                isLoad= isLoadCache3;
                isLoadCache3=false;
                break;
        }
        return isLoad;
    }


    public String getKeyName(int type){
        String name = null;
        switch (type){
            case NewsFragment.NEWS_TYPE_TOP:
                name="NEWS_TYPE_TOP";
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                name="NEWS_TYPE_NBA";
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                name="NEWS_TYPE_CARS";
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                name="NEWS_TYPE_JOKES";
                break;
        }
        return name;
    }

    @Override
    public void loadNewsDetail(final String docid, final OnLoadNewsDetailListener listener) {
        String url=getDetailUrl(docid);
        OkHttpUtils.ResultCallback<String> loadNewsCallback=new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                NewsDetailBean newsDetailBean=NewsJsonUtils.readJsonNewsDetailBeans(response,docid);
                listener.onSuccess(newsDetailBean);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load news detail info failure",e);
            }
        };
        OkHttpUtils.get(url,loadNewsCallback);

    }
    private String getDetailUrl(String docId) {
        StringBuffer sb = new StringBuffer(Urls.NEW_DETAIL);
        sb.append(docId).append(Urls.END_DETAIL_URL);
        return sb.toString();
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
