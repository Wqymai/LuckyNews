package com.android.luckynews.images;

import com.android.luckynews.bean.ImageBean;
import com.android.luckynews.utils.JsonUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuqiyan on 17/2/13.
 */

public class ImageJsonUtils {

    public static List<ImageBean> readJsonImageBeans(String res){
        List<ImageBean>  beans=new ArrayList<ImageBean>();
        try {
            JsonParser parser=new JsonParser();
            JsonArray jsonArray=parser.parse(res).getAsJsonArray();
            for (int i=1;i<jsonArray.size();i++){
                JsonObject jo=jsonArray.get(i).getAsJsonObject();
                ImageBean imageBean= JsonUtils.deserialize(jo,ImageBean.class);
                beans.add(imageBean);
            }
        }
        catch (Exception e){

        }
        return beans;
    }

}
