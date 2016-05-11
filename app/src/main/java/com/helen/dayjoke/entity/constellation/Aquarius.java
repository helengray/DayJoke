package com.helen.dayjoke.entity.constellation;

import com.helen.dayjoke.R;

/**
 * Created by Helen on 2016/5/4.
 * 水瓶座
 */
public class Aquarius extends Constellation {
    @Override
    public String getName() {
        return "水瓶座";
    }

    @Override
    public String getDate() {
        return "1/20-2/18";
    }

    @Override
    public int getResId() {
        return R.drawable.shuiping;
    }
}
