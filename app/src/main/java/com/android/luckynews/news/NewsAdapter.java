package com.android.luckynews.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.luckynews.R;
import com.android.luckynews.bean.NewsBean;
import com.android.luckynews.utils.ImageLoaderUtils;

import java.util.List;

/**
 * Created by wuqiyan on 17/1/19.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM=0;
    private static final int TYPE_FOOTER=1;

    private List<NewsBean> mData;
    private boolean mShowFooter=true;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public NewsAdapter(Context context){
        this.mContext=context;
    }
    public void setmData(List<NewsBean> data){
        this.mData=data;
        this.notifyDataSetChanged();
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE_ITEM){
            View vItem= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_news,parent,false);
            return new ItemViewHolder(vItem);
        }
        else {
            View vFoot=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer,null);
            vFoot.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(vFoot);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder){
            NewsBean news=mData.get(position);
            if (news==null){
                return;
            }
            ((ItemViewHolder) holder).mTitle.setText(news.getTitle());
            ((ItemViewHolder) holder).mDesc.setText(news.getDigest());
            ImageLoaderUtils.display(mContext,((ItemViewHolder) holder).mNewsImg,news.getImgsrc());

        }


    }

    @Override
    public int getItemCount() {
        int begin=mShowFooter?1:0;
        if (mData==null){
            return begin;
        }
        return mData.size()+begin;
    }

    @Override
    public int getItemViewType(int position) {
        if (!mShowFooter){
            return TYPE_ITEM;
        }
        if (position+1==getItemCount()){
            return TYPE_FOOTER;
        }
        else {
            return TYPE_ITEM;
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }
    public NewsBean getItem(int position){
        return mData==null?null:mData.get(position);
    }
    public void isShowFooter(boolean showFooter){
        this.mShowFooter=showFooter;
    }
    public  boolean isShowFooter(){
        return mShowFooter;
    }
    public class FooterViewHolder extends RecyclerView.ViewHolder{

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
    public class ItemViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTitle;
        public TextView mDesc;
        public ImageView mNewsImg;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTitle= (TextView) itemView.findViewById(R.id.tvTitle);
            mDesc= (TextView) itemView.findViewById(R.id.tvDesc);
            mNewsImg= (ImageView) itemView.findViewById(R.id.ivNews);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener!=null){
                mOnItemClickListener.onItemClick(v,this.getPosition());
            }
        }
    }

}
