package com.helen.dayjoke.ui.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.helen.dayjoke.R;
import com.helen.dayjoke.entity.QiuTuEn;
import com.helen.dayjoke.ui.application.Constant;
import com.helen.dayjoke.ui.view.GalleryDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Helen on 2016/4/27.
 *
 */
public class QiuTuAdapter extends BaseRecyclerAdapter<QiuTuEn> implements View.OnClickListener{


    public QiuTuAdapter(List<QiuTuEn> dataList) {
        super(dataList);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        return new QiuTuViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_qiu_tu, parent, false));
    }

    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder holder, int position) {
        QiuTuViewHolder viewHolder = (QiuTuViewHolder) holder;
        QiuTuEn qiuTuEn = getItem(position);
        viewHolder.mTextTime.setText(qiuTuEn.getTime());
        viewHolder.mTextTitle.setText(qiuTuEn.getContent());
        viewHolder.mPic.setImageURI(qiuTuEn.getPic());
        viewHolder.mPic.setOnClickListener(this);
        viewHolder.mPic.setTag(R.id.pic,qiuTuEn);
    }
    private GalleryDialog mGalleryDialog;
    @Override
    public void onClick(View v) {
        MobclickAgent.onEvent(v.getContext(), Constant.Event.EVENT_ID_TAB_QIU_DETAIL);
        if(mGalleryDialog == null){
            mGalleryDialog = new GalleryDialog(v.getContext());
        }
        QiuTuEn qiuTuEn = (QiuTuEn) v.getTag(R.id.pic);
        String url = qiuTuEn.getPic() ;
        mGalleryDialog.showGallery(getImageUris(),Uri.parse(url),null);
    }

    private List<Uri> getImageUris(){
        List<Uri> imgs = new ArrayList<>();
        if(mDataList != null && !mDataList.isEmpty()){
            int size = mDataList.size();
            for (int i=0;i<size;i++){
                Uri uri = Uri.parse(mDataList.get(i).getPic());
                imgs.add(uri);
            }
        }
        return imgs;
    }

    /**
     * Created by 李晓伟 on 2016/4/27.
     *
     */
    class QiuTuViewHolder extends RecyclerView.ViewHolder{
        TextView mTextTitle;
        TextView mTextTime;
        SimpleDraweeView mPic;

        public QiuTuViewHolder(View itemView) {
            super(itemView);
            mTextTitle = (TextView) itemView.findViewById(R.id.text_title);
            mTextTime = (TextView) itemView.findViewById(R.id.text_time);
            mPic = (SimpleDraweeView) itemView.findViewById(R.id.pic);

        }
    }
}
