package com.helen.dayjoke.entity;

import com.helen.dayjoke.R;

/**
 * Created by 李晓伟 on 2016/5/4.
 * 双鱼座
 */
public class Pisces extends Constellation{
    @Override
    public String getName() {
        return "双鱼座";
    }

    @Override
    public String getDate() {
        return "2/19-3/20";
    }

    @Override
    public int getResId() {
        return R.drawable.shuangyu;
    }
}
