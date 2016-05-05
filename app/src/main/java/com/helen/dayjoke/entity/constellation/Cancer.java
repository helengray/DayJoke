package com.helen.dayjoke.entity.constellation;

import com.helen.dayjoke.R;
import com.helen.dayjoke.entity.constellation.Constellation;

/**
 * Created by 李晓伟 on 2016/5/4.
 * 巨蟹座
 */
public class Cancer extends Constellation {
    @Override
    public String getName() {
        return "巨蟹座";
    }

    @Override
    public String getDate() {
        return "6/22-7/22";
    }

    @Override
    public int getResId() {
        return R.drawable.juxie;
    }
}
