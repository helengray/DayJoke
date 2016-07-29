package com.helen.dayjoke.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.helen.dayjoke.R;
import com.helen.dayjoke.entity.JokeEn;
import com.helen.dayjoke.ui.activity.JokeTextDetailActivity;
import com.helen.dayjoke.ui.application.Constant;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

/**
 * Created by Helen on 2016/4/27.
 *
 */
public class JokeTextAdapter extends BaseRecyclerAdapter<JokeEn>{

    public JokeTextAdapter(List<JokeEn> dataList) {
        super(dataList);
    }


    @Override
    protected RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        return new JokeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_joke_text, parent, false));
    }


    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder holder, int position) {
        JokeViewHolder jokeViewHolder = (JokeViewHolder) holder;
        JokeEn jokeEn = getItem(position);
        jokeViewHolder.setBean(jokeEn);
        jokeViewHolder.mTextTitle.setText(jokeEn.getTitle());
        jokeViewHolder.mTextTime.setText(jokeEn.getTimeFormat());
        jokeViewHolder.mTextContent.setText(Html.fromHtml(jokeEn.getContent()));
    }


    /**
     * Created by 李晓伟 on 2016/4/27.
     *
     */
    private class JokeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTextTitle;
        TextView mTextTime;
        TextView mTextContent;
        private JokeEn mBean;
        public JokeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTextTitle = (TextView) itemView.findViewById(R.id.text_title);
            mTextTime = (TextView) itemView.findViewById(R.id.text_time);
            mTextContent = (TextView) itemView.findViewById(R.id.text_content);
        }

        public void setBean(JokeEn jokeEn){
            this.mBean = jokeEn;
        }

        @Override
        public void onClick(View v) {
            JokeTextDetailActivity.launcher(v.getContext(),mBean);
            MobclickAgent.onEvent(v.getContext(), Constant.Event.EVENT_ID_TAB_TEXT_DETAIL);
        }
    }


}
