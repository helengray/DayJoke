package com.helen.dayjoke.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.helen.dayjoke.R;

/**
 * Created by 李晓伟 on 2016/5/5.
 *
 */
public class TitlebarActivity extends BaseActivity{
    private Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View v = LayoutInflater.from(this).inflate(layoutResID,null);
        setContentView(v);
    }

    @Override
    public void setContentView(View view) {
        View titleView = LayoutInflater.from(this).inflate(R.layout.title_layout,null);

        mToolbar = (Toolbar) titleView.findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        RelativeLayout layoutContent = (RelativeLayout) titleView.findViewById(R.id.layout_content);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutContent.addView(view,params);

        super.setContentView(titleView);
    }

    @Override
    public void setTitle(CharSequence title) {
        mToolbar.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        mToolbar.setTitle(titleId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
