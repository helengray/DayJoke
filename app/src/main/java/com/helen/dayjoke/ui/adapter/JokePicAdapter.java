package com.helen.dayjoke.ui.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.helen.dayjoke.R;
import com.helen.dayjoke.entity.JokeEn;
import com.helen.dayjoke.ui.view.GalleryDialog;

import java.util.List;

/**
 * Created by Helen on 2016/4/27.
 *
 */
public class JokePicAdapter extends BaseRecyclerAdapter<JokeEn>{


    public JokePicAdapter(List<JokeEn> dataList) {
        super(dataList);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        return new JokeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_joke_pic, parent, false));
    }

    private GalleryDialog mGalleryDialog;

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mGalleryDialog == null){
                mGalleryDialog = new GalleryDialog(v.getContext());
            }
            JokeEn jokeEn = (JokeEn) v.getTag(R.id.pic);
            String url = jokeEn.getImg() ;
            String title = jokeEn.getTitle();
            mGalleryDialog.showGallery(Uri.parse(url),title);
        }
    };


    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder holder, int position) {
        JokeViewHolder jokeViewHolder = (JokeViewHolder) holder;
        JokeEn jokeEn = getItem(position);
        jokeViewHolder.mTextTitle.setText(jokeEn.getTitle());
        jokeViewHolder.mTextTime.setText(jokeEn.getTimeFormat());
        jokeViewHolder.mPic.setImageURI(Uri.parse(jokeEn.getImg()));
        jokeViewHolder.mPic.setOnClickListener(mClickListener);
        jokeViewHolder.mPic.setTag(R.id.pic,jokeEn);
    }

    /**
     * Created by 李晓伟 on 2016/4/27.
     *
     */
    class JokeViewHolder extends RecyclerView.ViewHolder{
        TextView mTextTitle;
        TextView mTextTime;
        SimpleDraweeView mPic;

        public JokeViewHolder(View itemView) {
            super(itemView);
            mTextTitle = (TextView) itemView.findViewById(R.id.text_title);
            mTextTime = (TextView) itemView.findViewById(R.id.text_time);
            mPic = (SimpleDraweeView) itemView.findViewById(R.id.pic);

        }
    }

}
