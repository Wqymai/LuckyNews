package com.android.luckynews.news.presenter;

import com.android.luckynews.bean.NewsBean;
import com.android.luckynews.commons.Urls;
import com.android.luckynews.news.model.NewsModel;
import com.android.luckynews.news.model.NewsModelImpl;
import com.android.luckynews.news.model.OnLoadNewsListListener;
import com.android.luckynews.news.view.NewsView;
import com.android.luckynews.news.widget.NewsFragment;

import java.util.List;

/**
 * Created by wuqiyan on 17/1/19.
 */
public class NewsPresenterImpl implements NewsPresenter,OnLoadNewsListListener {
    private NewsView mNewsView;
    private NewsModel mNewsModel;

    public NewsPresenterImpl(NewsView newsView){
        this.mNewsView=newsView;
        this.mNewsModel=new NewsModelImpl();

    }
    @Override
    public void loadNews(int type, int pageIndex) {
        String url=getUrl(type,pageIndex);
        if (pageIndex==0){
            mNewsView.showProgress();
        }
        mNewsModel.loadNews(url,type,this);

    }

    private String getUrl(int type, int pageIndex) {
        StringBuilder sb=new StringBuilder();
        switch (type) {
            case NewsFragment.NEWS_TYPE_TOP:
                sb.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                sb.append(Urls.COMMON_URL).append(Urls.NBA_ID);
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                sb.append(Urls.COMMON_URL).append(Urls.CAR_ID);
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                sb.append(Urls.COMMON_URL).append(Urls.JOKE_ID);
                break;
            default:
                sb.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
        }
        sb.append("/").append(pageIndex).append(Urls.END_URL);
        return sb.toString();
    }

    @Override
    public void onSuccess(List<NewsBean> list) {
        mNewsView.hideProgress();
        mNewsView.addNews(list);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mNewsView.hideProgress();
        mNewsView.showLoadFailMsg();
    }
}
