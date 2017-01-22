package com.android.luckynews.news.widget;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.luckynews.R;
import com.android.luckynews.bean.NewsBean;
import com.android.luckynews.news.presenter.INewsDetailPresenter;
import com.android.luckynews.news.presenter.NewsDetailPresenterImpl;
import com.android.luckynews.news.view.INewsDetailView;
import com.android.luckynews.utils.ImageLoaderUtils;
import com.android.luckynews.utils.ToolsUtil;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by wuqiyan on 17/1/22.
 */
public class NewsDetailActivity extends SwipeBackActivity implements INewsDetailView {

    private NewsBean mNews;
    private HtmlTextView mTVNewsContent;
    private INewsDetailPresenter mNewsDetailPresenter;
    private ProgressBar mProgressBar;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        mProgressBar= (ProgressBar) findViewById(R.id.progress);
        mTVNewsContent= (HtmlTextView) findViewById(R.id.htNewsContent);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSwipeBackLayout=getSwipeBackLayout();
        mSwipeBackLayout.setEdgeSize(ToolsUtil.getWidthInPx(this));
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        mNews= (NewsBean) getIntent().getSerializableExtra("news");
        Log.i("TAG",mNews.toString());

        CollapsingToolbarLayout collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(mNews.getTitle());
        ImageLoaderUtils.display(getApplicationContext(), (ImageView) findViewById(R.id.ivImage),mNews.getImgsrc());

        mNewsDetailPresenter=new NewsDetailPresenterImpl(getApplication(),this);
        mNewsDetailPresenter.loadNewsDetail(mNews.getDocid());

    }

    @Override
    public void showNewsDetailContent(String newsDetailContent) {
        mTVNewsContent.setHtmlFromString(newsDetailContent,new HtmlTextView.LocalImageGetter());
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }
}
