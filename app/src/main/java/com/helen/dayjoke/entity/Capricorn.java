package com.helen.dayjoke.entity;

import com.helen.dayjoke.R;

/**
 * Created by 李晓伟 on 2016/5/4.
 * 摩羯座
 */
public class Capricorn extends Constellation{
    @Override
    public String getName() {
        return "摩羯座";
    }

    @Override
    public String getDate() {
        return "12/22-1/19";
    }

    @Override
    public int getResId() {
        return R.drawable.mojie;
    }
}
