package com.android.luckynews.main.presenter;

import com.android.luckynews.R;
import com.android.luckynews.main.view.MainView;

/**
 * Created by wuqiyan on 17/1/19.
 */
public class MainPresenterImpl implements MainPresenter {
    private MainView mMainView;
    public MainPresenterImpl(MainView mainView){
        this.mMainView=mainView;
    }
    @Override
    public void switchNavigation(int id) {
        switch (id){
            case R.id.navigation_item_news:
                mMainView.switch2News();
                break;
            case R.id.navigation_item_images:
                mMainView.switch2Images();
                break;
            case R.id.navigation_item_weather:
                mMainView.switch2Weather();
                break;
            case R.id.navigation_item_about:
                mMainView.switch2About();
                break;
        }
    }
}
