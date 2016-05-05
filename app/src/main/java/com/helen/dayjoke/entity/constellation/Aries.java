package com.helen.dayjoke.entity.constellation;

import com.helen.dayjoke.R;
import com.helen.dayjoke.entity.constellation.Constellation;

/**
 * Created by 李晓伟 on 2016/5/4.
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
