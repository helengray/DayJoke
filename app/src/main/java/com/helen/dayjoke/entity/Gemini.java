package com.helen.dayjoke.entity;

import com.helen.dayjoke.R;

/**
 * Created by 李晓伟 on 2016/5/4.
 * 双子座
 */
public class Gemini extends Constellation{
    @Override
    public String getName() {
        return "双子座";
    }

    @Override
    public String getDate() {
        return "5/21-6/21";
    }

    @Override
    public int getResId() {
        return R.drawable.shuangzi;
    }
}
