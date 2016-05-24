package com.helen.dayjoke.ui.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.helen.dayjoke.R;
import com.helen.dayjoke.entity.Mito;
import com.helen.dayjoke.ui.view.EmptyEmbeddedContainer;
import com.helen.dayjoke.ui.view.GalleryDialog;

import java.util.List;

/**
 * Created by Helen on 2016/4/27.
 *
 */
public class WelfareAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Mito> mMitos;
    private static final int TYPE_CONTENT = 1;
    private static final int TYPE_FOOTER = 2;

    public final static int STATUS_NORMAL = 0;
    public final static int STATUS_LOADING = 1;
    public final static int STATUS_LOAD_FAIL = 2;
    private int status = STATUS_NORMAL;

    public WelfareAdapter(List<Mito> jokeEns){
        this.mMitos = jokeEns;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //if(viewType == TYPE_CONTENT){
            return new WelfareViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_welfare, parent, false));
        /*}else {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.xlistview_footer, parent, false));
        }*/
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
        //if(holder instanceof WelfareViewHolder){
            WelfareViewHolder welfareViewHolder = (WelfareViewHolder) holder;
            Mito mito = mMitos.get(position);
            welfareViewHolder.mTextTitle.setText(mito.getTitle());
            welfareViewHolder.mImageView.setImageURI(Uri.parse(mito.getThumb()));
            welfareViewHolder.mTextTime.setText(mito.getTimeFormat());
            welfareViewHolder.itemView.setOnClickListener(mClickListener);
            welfareViewHolder.itemView.setTag(mito);
        /*}else {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            switch (status){
                case STATUS_LOADING:
                    footerViewHolder.mProgressBar.setVisibility(View.VISIBLE);
                    footerViewHolder.mTextMore.setText(R.string.loading);
                    break;
                case STATUS_LOAD_FAIL:
                    footerViewHolder.mProgressBar.setVisibility(View.GONE);
                    footerViewHolder.mTextMore.setText(R.string.load_fail_retry);
                    break;
                case STATUS_NORMAL:
                default:
                    footerViewHolder.mProgressBar.setVisibility(View.VISIBLE);
                    footerViewHolder.mTextMore.setText(R.string.loading);
                    break;
            }
        }*/
    }

    @Override
    public int getItemCount() {
        if(mMitos != null && !mMitos.isEmpty()){
            return mMitos.size();
        }
        return 0;
    }

    public void notifyDataSetChanged(int status){
        this.status = status;
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(position + 1 == getItemCount()){
            return TYPE_FOOTER;
        }else {
            return TYPE_CONTENT;
        }
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

    private EmptyEmbeddedContainer.EmptyInterface emptyInterface;

    public void setEmptyInterface(EmptyEmbeddedContainer.EmptyInterface emptyInterface) {
        this.emptyInterface = emptyInterface;
    }


    private class FooterViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextMore;
        private ProgressBar mProgressBar;
        public FooterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(emptyInterface != null){
                        emptyInterface.doRetry();
                    }
                    mProgressBar.setVisibility(View.VISIBLE);
                    mTextMore.setText(R.string.loading);
                }
            });
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            mTextMore = (TextView) itemView.findViewById(R.id.view_more);
        }
    }

}
