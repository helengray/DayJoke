package com.helen.dayjoke.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.helen.dayjoke.R;
import com.helen.dayjoke.entity.Mito;
import com.helen.dayjoke.ui.adapter.JokePagerAdapter;
import com.helen.dayjoke.ui.fragment.WelfareFragment;

/**
 * Created by 李晓伟 on 2016/5/24.
 * 福利
 */
public class WelfareActivity extends BaseActivity{

    public static void launcher(Context context){
        context.startActivity(new Intent(context,WelfareActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welfare_activity);
        initView();
    }

    private void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        JokePagerAdapter pagerAdapter = new JokePagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(WelfareFragment.newInstance(0),getString(R.string.sui_ji));
        pagerAdapter.addFragment(WelfareFragment.newInstance(Mito.TYPE_DA_MEI),getString(R.string.da_mei));
        pagerAdapter.addFragment(WelfareFragment.newInstance(Mito.TYPE_XIAO_QING_XIN),getString(R.string.xiao_qing_xin));
        pagerAdapter.addFragment(WelfareFragment.newInstance(Mito.TYPE_WEN_YI),getString(R.string.wen_yi));
        pagerAdapter.addFragment(WelfareFragment.newInstance(Mito.TYPE_XING_GAN),getString(R.string.xing_gan));
        pagerAdapter.addFragment(WelfareFragment.newInstance(Mito.TYPE_DA_CHANGG_TUI),getString(R.string.da_chang_tui));
        pagerAdapter.addFragment(WelfareFragment.newInstance(Mito.TYPE_HEI_SI),getString(R.string.hei_si));
        pagerAdapter.addFragment(WelfareFragment.newInstance(Mito.TYPE_XIAO_QIAO_TUN),getString(R.string.xiao_qiao_tun));
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
