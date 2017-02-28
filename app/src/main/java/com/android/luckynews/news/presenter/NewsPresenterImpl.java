package com.android.luckynews.news.presenter;

import com.android.luckynews.bean.NewsBean;
import com.android.luckynews.commons.Urls;
import com.android.luckynews.news.model.INewsModel;
import com.android.luckynews.news.model.NewsModelImpl;
import com.android.luckynews.news.model.OnLoadNewsListListener;
import com.android.luckynews.news.view.INewsView;
import com.android.luckynews.news.widget.NewsFragment;
import com.android.luckynews.utils.LogUtils;

import java.util.List;

/**
 * Created by wuqiyan on 17/1/19.
 */
public class NewsPresenterImpl implements INewsPresenter, OnLoadNewsListListener {

    private static final String TAG = "NewsPresenterImpl";

    private INewsView mNewsView;
    private INewsModel mNewsModel;

    public NewsPresenterImpl(INewsView newsView) {
        this.mNewsView = newsView;
        this.mNewsModel = new NewsModelImpl();
    }

    @Override
    public void loadNews(final int type, final int pageIndex,final boolean isRefresh,final boolean isSaveCache) {
        String url = getUrl(type, pageIndex);
        LogUtils.d(TAG, url);
        //只有第一页的或者刷新的时候才显示刷新进度条
        if(pageIndex == 0) {
            mNewsView.showProgress();
        }

        mNewsModel.loadNews(url, type, this,isRefresh,isSaveCache);
    }

    /**
     * 根据类别和页面索引创建url
     * @param type
     * @param pageIndex
     * @return
     */
    private String getUrl(int type, int pageIndex) {
        StringBuffer sb = new StringBuffer();
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
