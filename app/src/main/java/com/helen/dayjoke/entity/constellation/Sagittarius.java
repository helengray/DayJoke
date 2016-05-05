package com.helen.dayjoke.entity.constellation;

import com.helen.dayjoke.R;
import com.helen.dayjoke.entity.constellation.Constellation;

/**
 * Created by 李晓伟 on 2016/5/4.
 * 射手座
 */
public class Sagittarius extends Constellation {
    @Override
    public String getName() {
        return "射手座";
    }

    @Override
    public String getDate() {
        return "11/23-12/21";
    }

    @Override
    public int getResId() {
        return R.drawable.sheshou;
    }
}
