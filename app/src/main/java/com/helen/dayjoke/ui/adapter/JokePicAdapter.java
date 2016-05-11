package com.helen.dayjoke.ui.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.helen.dayjoke.R;
import com.helen.dayjoke.entity.JokeEn;
import com.helen.dayjoke.ui.view.EmptyEmbeddedContainer;
import com.helen.dayjoke.ui.view.GalleryDialog;

import java.util.List;

/**
 * Created by 李晓伟 on 2016/4/27.
 *
 */
public class JokePicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<JokeEn> jokeEnList;
    private static final int TYPE_CONTENT = 1;
    private static final int TYPE_FOOTER = 2;

    public final static int STATUS_NORMAL = 0;
    public final static int STATUS_LOADING = 1;
    public final static int STATUS_LOAD_FAIL = 2;
    private int status = STATUS_NORMAL;

    public JokePicAdapter(List<JokeEn> jokeEns){
        this.jokeEnList = jokeEns;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_CONTENT){
            return new JokeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_joke_pic, parent, false));
        }else {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.xlistview_footer, parent, false));
        }
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof JokeViewHolder){
            JokeViewHolder jokeViewHolder = (JokeViewHolder) holder;
            JokeEn jokeEn = jokeEnList.get(position);
            jokeViewHolder.mTextTitle.setText(jokeEn.getTitle());
            jokeViewHolder.mTextTime.setText(jokeEn.getTime());
            jokeViewHolder.mPic.setImageURI(Uri.parse(jokeEn.getImg()));
            jokeViewHolder.mPic.setOnClickListener(mClickListener);
            jokeViewHolder.mPic.setTag(R.id.pic,jokeEn);
        }else {
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
        }
    }

    @Override
    public int getItemCount() {
        if(jokeEnList != null){
            if(jokeEnList.isEmpty()){
                return 0;
            }
            return jokeEnList.size()+1;
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

    private EmptyEmbeddedContainer.EmptyInterface emptyInterface;

    public void setEmptyInterface(EmptyEmbeddedContainer.EmptyInterface emptyInterface) {
        this.emptyInterface = emptyInterface;
    }


    class FooterViewHolder extends RecyclerView.ViewHolder{
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
