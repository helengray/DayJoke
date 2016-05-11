package com.helen.dayjoke.entity.constellation;

import com.helen.dayjoke.R;

/**
 * Created by Helen on 2016/5/4.
 * 白羊座
 */
public class Aries extends Constellation {
    @Override
    public String getName() {
        return "白羊座";
    }

    @Override
    public String getDate() {
        return "3/21-4/19";
    }

    @Override
    public int getResId() {
        return R.drawable.baiyang;
    }
}
