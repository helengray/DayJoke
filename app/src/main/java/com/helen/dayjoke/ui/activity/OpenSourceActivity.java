package com.helen.dayjoke.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.helen.dayjoke.R;

/**
 * Created by 李晓伟 on 2016/5/10.
 *
 */
public class OpenSourceActivity extends BaseActivity{

    public static void launcher(Context context){
        context.startActivity(new Intent(context,OpenSourceActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_source);
    }
}
