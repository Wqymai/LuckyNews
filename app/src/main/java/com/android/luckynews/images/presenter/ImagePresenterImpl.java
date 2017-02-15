package com.android.luckynews.images.presenter;

import com.android.luckynews.bean.ImageBean;
import com.android.luckynews.images.model.ImageModel;
import com.android.luckynews.images.model.ImageModelImpl;
import com.android.luckynews.images.view.ImageView;

import java.util.List;

/**
 * Created by wuqiyan on 17/2/7.
 */

public class ImagePresenterImpl implements ImagePresenter,ImageModelImpl.OnLoadImageListListener {

    private ImageModel mImageModel;
    private ImageView mImageView;

    public ImagePresenterImpl(ImageView imageView){
        this.mImageModel=new ImageModelImpl();
        this.mImageView=imageView;
    }


    @Override
    public void loadImageList() {
        mImageView.showProgress();
        mImageModel.loadImageList(this);

    }

    @Override
    public void onSuccess(List<ImageBean> list) {
        mImageView.addImages(list);
        mImageView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mImageView.hideProgress();
        mImageView.showLoadFailMsg();

    }
}
