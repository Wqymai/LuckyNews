package com.android.luckynews.images.model;

import com.android.luckynews.bean.ImageBean;
import com.android.luckynews.commons.Urls;
import com.android.luckynews.images.ImageJsonUtils;
import com.android.luckynews.utils.OkHttpUtils;

import java.util.List;

/**
 * Created by wuqiyan on 17/2/7.
 */

public class ImageModelImpl implements ImageModel {
    @Override
    public void loadImageList(final OnLoadImageListListener listener) {
        String url= Urls.IMAGES_URL;
        OkHttpUtils.ResultCallback<String> loadImageCallback=new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                List<ImageBean> imageBeanList= ImageJsonUtils.readJsonImageBeans(response);
                listener.onSuccess(imageBeanList);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load image list failure.", e);
            }

        };
        OkHttpUtils.get(url, loadImageCallback);

    }

    public interface OnLoadImageListListener{
        void onSuccess(List<ImageBean> list);
        void onFailure(String msg,Exception e);
    }
}
