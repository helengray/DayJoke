package com.helen.dayjoke.entity.constellation;

import com.helen.dayjoke.R;

/**
 * Created by Helen on 2016/5/4.
 * 狮子座
 */
public class Leo extends Constellation {
    @Override
    public String getName() {
        return "狮子座";
    }

    @Override
    public String getDate() {
        return "7/23-8/22";
    }

    @Override
    public int getResId() {
        return R.drawable.shizi;
    }
}
