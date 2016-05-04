package com.helen.dayjoke.entity;

import com.helen.dayjoke.R;

/**
 * Created by 李晓伟 on 2016/5/4.
 * 天秤座
 */
public class Libra extends Constellation{
    @Override
    public String getName() {
        return "天秤座";
    }

    @Override
    public String getDate() {
        return "9/23-10/23";
    }

    @Override
    public int getResId() {
        return R.drawable.tiancheng;
    }
}
