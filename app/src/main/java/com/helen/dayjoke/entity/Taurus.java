package com.helen.dayjoke.entity;

import com.helen.dayjoke.R;

/**
 * Created by 李晓伟 on 2016/5/4.
 * 金牛座
 */
public class Taurus extends Constellation{
    @Override
    public String getName() {
        return "金牛座";
    }

    @Override
    public String getDate() {
        return "4/20-5/20";
    }

    @Override
    public int getResId() {
        return R.drawable.jinniu;
    }
}
