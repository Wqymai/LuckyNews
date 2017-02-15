package com.android.luckynews.images.view;

import com.android.luckynews.bean.ImageBean;

import java.util.List;

/**
 * Created by wuqiyan on 17/2/7.
 */

public interface ImageView {

    void addImages(List<ImageBean> list);
    void showProgress();
    void hideProgress();
    void showLoadFailMsg();

}
