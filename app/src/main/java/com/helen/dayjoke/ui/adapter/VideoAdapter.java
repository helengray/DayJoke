package com.helen.dayjoke.ui.adapter;

import android.graphics.drawable.Animatable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.helen.dayjoke.R;
import com.helen.dayjoke.entity.VideoEn;
import com.helen.dayjoke.ui.activity.VideoPlayActivity;

import java.util.List;

/**
 * Created by Helen on 2016/4/27.
 *
 */
public class VideoAdapter extends BaseRecyclerAdapter<VideoEn> implements View.OnClickListener{
    public VideoAdapter(List<VideoEn> dataList) {
        super(dataList);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_video, parent, false));
    }

    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder holder, int position) {
        final VideoViewHolder viewHolder = (VideoViewHolder) holder;
        VideoEn videoEn = getItem(position);
        viewHolder.mTextTitle.setText(videoEn.getContent());
        PipelineDraweeControllerBuilder builder = Fresco.newDraweeControllerBuilder();
        builder.setControllerListener(new BaseControllerListener<ImageInfo>(){
            @Override
            public void onFailure(String id, Throwable throwable) {
                super.onFailure(id, throwable);
                viewHolder.mImageViewVideo.setVisibility(View.GONE);
            }

            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                viewHolder.mImageViewVideo.setVisibility(View.VISIBLE);
            }
        });
        builder.setUri(videoEn.getPic_url());
        viewHolder.mPic.setController(builder.build());
        viewHolder.mPic.setOnClickListener(this);
        viewHolder.mPic.setTag(R.id.pic,videoEn);
    }
    @Override
    public void onClick(View v) {
        VideoEn videoEn = (VideoEn) v.getTag(R.id.pic);
        VideoPlayActivity.launcher(v.getContext(),videoEn.getHigh_url(),videoEn.getLow_url(),videoEn.getPic_url());
    }

    /**
     * Created by 李晓伟 on 2016/4/27.
     *
     */
    class VideoViewHolder extends RecyclerView.ViewHolder{
        TextView mTextTitle;
        SimpleDraweeView mPic;
        ImageView mImageViewVideo;

        public VideoViewHolder(View itemView) {
            super(itemView);
            mTextTitle = (TextView) itemView.findViewById(R.id.text_title);
            mPic = (SimpleDraweeView) itemView.findViewById(R.id.pic);
            mImageViewVideo = (ImageView) itemView.findViewById(R.id.iv_video);
        }
    }
}
