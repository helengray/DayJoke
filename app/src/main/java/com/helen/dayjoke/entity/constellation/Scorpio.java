package com.helen.dayjoke.entity.constellation;

import com.helen.dayjoke.R;
import com.helen.dayjoke.entity.constellation.Constellation;

/**
 * Created by 李晓伟 on 2016/5/4.
 * 天蝎座
 */
public class Scorpio extends Constellation {
    @Override
    public String getName() {
        return "天蝎座";
    }

    @Override
    public String getDate() {
        return "10/24-11/22";
    }

    @Override
    public int getResId() {
        return R.drawable.mojie;
    }
}
