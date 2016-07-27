package com.helen.dayjoke.ui.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.helen.dayjoke.R;
import com.helen.dayjoke.entity.Mito;
import com.helen.dayjoke.ui.view.GalleryDialog;

import java.util.List;

/**
 * Created by Helen on 2016/4/27.
 *
 */
public class WelfareAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Mito> mMitos;

    public WelfareAdapter(List<Mito> jokeEns){
        this.mMitos = jokeEns;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WelfareViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_welfare, parent, false));
    }

    private GalleryDialog mGalleryDialog;
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mGalleryDialog == null){
                mGalleryDialog = new GalleryDialog(v.getContext());
            }
            Mito mito = (Mito) v.getTag();
            String url = mito.getThumb() ;
            String title = mito.getTitle();
            mGalleryDialog.showGallery(Uri.parse(url),title);
        }
    };


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WelfareViewHolder welfareViewHolder = (WelfareViewHolder) holder;
        Mito mito = mMitos.get(position);
        welfareViewHolder.mTextTitle.setText(mito.getTitle());
        welfareViewHolder.mImageView.setImageURI(Uri.parse(mito.getThumb()));
        welfareViewHolder.mTextTime.setText(mito.getTimeFormat());
        welfareViewHolder.itemView.setOnClickListener(mClickListener);
        welfareViewHolder.itemView.setTag(mito);
    }

    @Override
    public int getItemCount() {
        if(mMitos != null && !mMitos.isEmpty()){
            return mMitos.size();
        }
        return 0;
    }


    /**
     * Created by 李晓伟 on 2016/4/27.
     *
     */
    private class WelfareViewHolder extends RecyclerView.ViewHolder{
        TextView mTextTitle;
        TextView mTextTime;
        ImageView mImageView;
        public WelfareViewHolder(View itemView) {
            super(itemView);
            mTextTitle = (TextView) itemView.findViewById(R.id.item_title);
            mTextTime = (TextView) itemView.findViewById(R.id.item_time);
            mImageView = (ImageView) itemView.findViewById(R.id.item_img);
        }

    }

}
